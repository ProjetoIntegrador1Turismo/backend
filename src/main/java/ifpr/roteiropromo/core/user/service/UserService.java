package ifpr.roteiropromo.core.user.service;


import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTO;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTOForm;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTORecovery;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.repository.UserRepository;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {


    private final JwtTokenHandler jwtTokenHandler;
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    public UserDTO creatNewUser(UserDTOForm userDTOForm){

        ResponseEntity<Map> response;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.postForEntity(
                    "http://localhost:8080/admin/realms/SpringBootKeycloak/users",
                    createRequestToKeycloackToNewUser(userDTOForm), Map.class);
        }catch (Exception e){
            throw  new ServiceError("E-mail already registered!");
        }
        if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(201))){
            if (userDTOForm.getCadasturCode() == null){
                Tourist tourist = mapper.map(userDTOForm, Tourist.class);
                tourist.setUserName(userDTOForm.getFirstName());
                return mapper.map(userRepository.save(tourist), UserDTO.class);
            }else{
                Guide guide = mapper.map(userDTOForm, Guide.class);
                guide.setUserName(userDTOForm.getFirstName());
                guide.setIsApproved(false);
                return mapper.map(userRepository.save(guide), UserDTO.class);
            }
        }else {
            throw new ServiceError("An error occurred when creating the new user.");
        }
    }

    private HttpEntity<String> createRequestToKeycloackToNewUser(UserDTOForm userDTOForm){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(jwtTokenHandler.getAdminToken());
        String userJson = String.format(
                "{" +
                        "\"enabled\": %b," +
                        "\"emailVerified\": %b," +
                        "\"firstName\": \"%s\"," +
                        "\"lastName\": \"%s\"," +
                        "\"email\": \"%s\"," +
                        "\"credentials\": [" +
                        "{" +
                        "\"type\": \"password\"," +
                        "\"value\": \"%s\"," +
                        "\"temporary\": false" +
                        "}" +
                        "]" +
                        "}",
                true, true, userDTOForm.getFirstName(), userDTOForm.getLastName(), userDTOForm.getEmail(), userDTOForm.getPassword());
        return new HttpEntity<String>(userJson, httpHeaders);

    }

//    private HttpEntity<String> createRequestToKeycloackToNewUser(UserDTOForm userDTOForm){
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.setBearerAuth(jwtTokenHandler.getAdminToken());
//        String userJson = String.format(
//                "{" +
//                        "\"username\": \"%s\"," +
//                        "\"enabled\": %b," +
//                        "\"emailVerified\": %b," +
//                        "\"firstName\": \"%s\"," +
//                        "\"lastName\": \"%s\"," +
//                        "\"email\": \"%s\"," +
//                        "\"credentials\": [" +
//                        "{" +
//                        "\"type\": \"password\"," +
//                        "\"value\": \"%s\"," +
//                        "\"temporary\": false" +
//                        "}" +
//                        "]" +
//                        "}",
//                userDTOForm.getUserName(), true, true, userDTOForm.getFirstName(), userDTOForm.getLastName(), userDTOForm.getEmail(), userDTOForm.getPassword());
//        return new HttpEntity<String>(userJson, httpHeaders);
//
//    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    //Esta funcionando a busca do usuário pelo email.
    //Metodo substituido pela rota que dispara o email e reset?
    public String resetUserPassword(UserDTORecovery userDTORecovery) {
        try{
            User userFound = userRepository.getOnByEmail(userDTORecovery.getEmail());
            getUserIdFromKeyclock(userFound);
            String userIDKeycloack = getUserIdFromKeyclock(userFound);
            log.info(userIDKeycloack);

            return "A password recovery email has been sent!";
        }catch (Exception e){
            throw new ServiceError("Could not found a user with that email: " + userDTORecovery.getEmail());
        }
    }

    //funcionando - lapidar para retornar só o ID necessário para o update
    private String getUserIdFromKeyclock(User user){
        RestTemplate restTemplate = new RestTemplate();
        String keycloakUrl = "http://localhost:8080/admin/realms/SpringBootKeycloak/users?search=" + user.getEmail();
        String token = jwtTokenHandler.getAdminToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        //ResponseEntity<String> response = restTemplate.exchange(keycloakUrl, HttpMethod.GET, entity, String.class);
        ResponseEntity<Map> response = restTemplate.exchange(keycloakUrl, HttpMethod.GET, entity, Map.class);
        log.info(response.getBody());
        return (String) response.getBody().get("id");
    }


    public User getOneByEmail(String email) {
        try{
            return userRepository.getOnByEmail(email);
        }catch (Exception e){
            throw new ServiceError("Could not found a user with that email: " + email);
        }
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new ServiceError("Could not found a user with that ID: " + id)
        );
    }

    public Tourist getTouristById(Long id) {
        User userFound = getOneById(id);
        if (userFound instanceof Tourist){
            return (Tourist) userFound;
        }else{
            throw new ServiceError("User found with id: " + id + " is not a tourist so he can't make comments!");
        }
    }

    public Guide findGuideById(Long id) {
        User userFound = getOneById(id);
        if (userFound instanceof Guide){
            return (Guide) userFound;
        }else{
            throw new ServiceError("User found with id: " + id + " is not a guide so he can't make comments!");
        }
    }

    public User getOneById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ServiceError("Could not find user with id: " + id)
        );
    }

    public Boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public User update(UserDTO userDTO){
        User user = findById(userDTO.getId());
        mapper.map(userDTO, user);
        return userRepository.save(user);
    }

//    public Guide makeGuide(Long id){
//        User user = findById(id);
//        if (user instanceof Guide){
//            return (Guide) user;
//        }else{
//            Guide guide = new Guide();
//            mapper.map(user, guide);
//            guide.setIsApproved(false);
//            userRepository.delete(user);
//            return userRepository.save(guide);
//        }
//    }

    public Tourist updateTourist(Tourist touristFound) {
        return userRepository.save(touristFound);
    }

    public Guide makeGuide(Long id, String cadasturCode){
        User user = findById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Guide guide = new Guide();
        mapper.map(user, guide);
        guide.setCadasturCode(cadasturCode);
        guide.setIsApproved(true);
        userRepository.delete(user);
        return userRepository.save(guide);
    }
}

