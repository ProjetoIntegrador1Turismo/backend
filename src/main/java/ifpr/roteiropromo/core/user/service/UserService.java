package ifpr.roteiropromo.core.user.service;


import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.errors.AuthenticationServerError;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.pagesource.domain.BasicGuideDTO;
import ifpr.roteiropromo.core.user.domain.dtos.*;
import ifpr.roteiropromo.core.user.domain.entities.Admin;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.repository.GuideRepository;
import ifpr.roteiropromo.core.user.repository.UserRepository;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
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
    private final GuideRepository guideRepository;


    public UserDTO createNewUser(UserDTOForm userDTOForm) {

        validateCadasturCodeIfGuide(userDTOForm);
        createUserOnResourceServer(userDTOForm);

        final String DEFAULT_IMAGE_URL = "http://localhost:8081/uploads/userplaceholder.png";

        User user;

        if (userDTOForm.isActiveAdmin()) {
            Admin admin = mapper.map(userDTOForm, Admin.class);
            admin.setUserName(userDTOForm.getFirstName());
            admin.setActiveAdmin(true);
            user = admin;


            try {
                String userId = getUserIdFromKeycloak(user);
                this.addRoleToUser(userId, jwtTokenHandler.getAdminToken(), "ADMIN");
            } catch (Exception e) {
                throw new ServiceError("Failed to assign role to user: " + e.getMessage());
            }

        } else if (userDTOForm.getCadasturCode() == null) {
            Tourist tourist = mapper.map(userDTOForm, Tourist.class);
            tourist.setUserName(userDTOForm.getFirstName());
            user = tourist;
        } else {
            Guide guide = mapper.map(userDTOForm, Guide.class);
            guide.setUserName(userDTOForm.getFirstName());
            guide.setIsApproved(false);
            user = guide;
        }

        user.setProfileImageUrl(DEFAULT_IMAGE_URL);

        User savedUser = userRepository.save(user);
        return mapper.map(savedUser, UserDTO.class);
    }

    private void validateCadasturCodeIfGuide(UserDTOForm userDTOForm) {
        if(userDTOForm.getCadasturCode() != null){
            Boolean cadasturExists = guideRepository.existsByCadasturCode(userDTOForm.getCadasturCode());
            log.info(cadasturExists);
            if(cadasturExists) {
                throw new AuthenticationServerError(
                        "CadasturCode already registered: " + userDTOForm.getCadasturCode(),
                        HttpStatus.CONFLICT
                );
            }
        }
    }

    private void createUserOnResourceServer(UserDTOForm userDTOForm){
        try {
            new RestTemplate().postForEntity(
                    "http://localhost:8080/admin/realms/SpringBootKeycloak/users",
                    createRequestToKeycloakToNewUser(userDTOForm), Map.class);
        } catch (HttpClientErrorException.Conflict e) {
            throw new AuthenticationServerError("User e-mail already registered!", HttpStatus.CONFLICT);
        } catch (HttpClientErrorException.BadRequest e){
            throw new AuthenticationServerError("Invalid user data.", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            throw new AuthenticationServerError("Error when try to access resource server.", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }


    // OBS: PARA UTILIZAR ESSE MÉTODO É PRECISO JÁ ESTAR COM O CONTEINER NOVO ATUALIZADO... (O COM TEMA DO APP)
    public String resetUserPassword(String email) {
        try {
            User userFound = getOneByEmail(email);
            String userIDKeycloak = this.getUserIdFromKeycloak(userFound);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(jwtTokenHandler.getAdminToken());

            String payload = "[\"UPDATE_PASSWORD\"]";
            HttpEntity<String> entity = new HttpEntity<>(payload, headers);

            String url = "http://localhost:8080/admin/realms/SpringBootKeycloak/users/" + userIDKeycloak + "/execute-actions-email";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return "Email enviado com sucesso!";
            } else {
                throw new ServiceError("Erro ao enviar email para recuperação de senha: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new ServiceError("Erro ao enviar email para recuperação de senha: " + e.getMessage());
        }
    }



    public User getOneByEmail(String email) {
        User userFound = userRepository.getOneByEmail(email);
        if(userFound == null){
            throw new ServiceError("Could not find a user with that email: " + email);
        }
        return userFound;
    }

    public Tourist getTouristByEmail(String email) {
        User userFound = userRepository.getOneByEmail(email);
        if(userFound == null){
            throw new ServiceError("Could not find a user with that email: " + email);
        }
        if (userFound instanceof Tourist){
            return mapper.map(userFound, Tourist.class);
        }else{
            throw new ServiceError("User found with email: " + email + " is not a tourist!");
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


    public TouristDTO getMeTourist() {
        AuthenticatedUserDTO userAuthenticated = jwtTokenHandler.getUserDataFromToken();
        Tourist touristFound = getTouristByEmail(userAuthenticated.getEmail());

        TouristDTO touristDTO = mapper.map(touristFound, TouristDTO.class);

        touristDTO.getInterestedItineraries().forEach(itinerary -> {
            Guide guide = guideRepository.getByItineraries(mapper.map(itinerary, Itinerary.class));
            itinerary.setGuide(mapper.map(guide, BasicGuideDTO.class));
        });


        return touristDTO;
    }


    public Guide findGuideById(Long id) {
        User userFound = getOneById(id);
        if (userFound instanceof Guide){
            return (Guide) userFound;
        }else{
            throw new ServiceError("User found with id: " + id + " is not a guide!");
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


    public void updateTourist(Tourist touristFound) {
        userRepository.save(touristFound);
    }


    public UserDTO updateUser(UserDTOUpdate userDTOUpdate) {
        User user = userRepository.getOneByEmail(jwtTokenHandler.getUserDataFromToken().getEmail());
        if (user == null) {
            throw new ServiceError("User not found with email: " + jwtTokenHandler.getUserDataFromToken().getEmail());
        }

        user.setFirstName(userDTOUpdate.getFirstName());
        user.setLastName(userDTOUpdate.getLastName());
        User updatedUser = userRepository.save(user);

        // Atualizar nome e senha no Keycloak
        try {
            updateUserInKeycloak(updatedUser, userDTOUpdate.getNewPassword());
        } catch (Exception e) {
            throw new ServiceError("Failed to update user in Keycloak: " + e.getMessage());
        }

        return mapper.map(updatedUser, UserDTO.class);
    }


    public void updateProfileImageUrl(String imageUrl) {
        AuthenticatedUserDTO authenticatedUser = jwtTokenHandler.getUserDataFromToken();
        User userFound = getOneByEmail(authenticatedUser.getEmail());
        userFound.setProfileImageUrl(imageUrl);
        userRepository.save(userFound);
    }



    private HttpEntity<String> createRequestToKeycloakToNewUser(UserDTOForm userDTOForm){

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
        return new HttpEntity<>(userJson, httpHeaders);
    }

    public String getUserIdFromKeycloak(User user) {
        RestTemplate restTemplate = new RestTemplate();
        String keycloakUrl = "http://localhost:8080/admin/realms/SpringBootKeycloak/users?search=" + user.getEmail();
        String token = jwtTokenHandler.getAdminToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);


        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                keycloakUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {});


        if (response.getStatusCode() == HttpStatus.OK) {
            List<Map<String, Object>> users = response.getBody();
            if (users != null && !users.isEmpty()) {
                return users.get(0).get("id").toString();
            } else {
                throw new ServiceError("User not found in Keycloak");
            }
        } else {
            log.error("Failed to retrieve user from Keycloak. Status: {}", response.getStatusCode());
            throw new ServiceError("Failed to retrieve user from Keycloak. Status: " + response.getStatusCode());
        }
    }


    private void updateUserInKeycloak(User user, String newPassword) {
        RestTemplate restTemplate = new RestTemplate();
        String userId = getUserIdFromKeycloak(user);

        String token = jwtTokenHandler.getAdminToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        Map<String, Object> updateNamePayload = new HashMap<>();
        updateNamePayload.put("firstName", user.getFirstName());
        updateNamePayload.put("lastName", user.getLastName());


        HttpEntity<Map<String, Object>> nameEntity = new HttpEntity<>(updateNamePayload, headers);
        try {
            restTemplate.exchange(
                    "http://localhost:8080/admin/realms/SpringBootKeycloak/users/" + userId,
                    HttpMethod.PUT, nameEntity, String.class);
        } catch (Exception e) {
            throw new ServiceError("Failed to update user name in Keycloak: " + e.getMessage());
        }

        // Atualizar senha no Keycloak
        if (newPassword != null && !newPassword.isEmpty()) {
            Map<String, Object> updatePasswordPayload = new HashMap<>();
            updatePasswordPayload.put("type", "password");
            updatePasswordPayload.put("value", newPassword);
            updatePasswordPayload.put("temporary", false);

            HttpEntity<Map<String, Object>> passwordEntity = new HttpEntity<>(updatePasswordPayload, headers);
            try {
                restTemplate.exchange(
                        "http://localhost:8080/admin/realms/SpringBootKeycloak/users/" + userId + "/reset-password",
                        HttpMethod.PUT, passwordEntity, String.class);
            } catch (Exception e) {
                throw new ServiceError("Failed to update user password in Keycloak: " + e.getMessage());
            }
        }
    }


    public void addRoleToUser(String userId, String adminMasterToken, String roleName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + adminMasterToken);

        String roleId = getRoleId(roleName, adminMasterToken);

        // Payload que representa a role a ser adicionada
        String roleRepresentation = "[{\"id\": \"" + roleId + "\", \"name\": \"" + roleName + "\"}]";

        HttpEntity<String> entity = new HttpEntity<>(roleRepresentation, headers);
        restTemplate.exchange(
                "http://localhost:8080/admin/realms/SpringBootKeycloak/users/" + userId + "/role-mappings/realm",
                HttpMethod.POST, entity, String.class);
    }


    private String getRoleId(String role, String adminMasterToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + adminMasterToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "http://localhost:8080/admin/realms/SpringBootKeycloak/roles";

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {});

        return response.getBody().stream()
                .filter(map -> role.equals(map.get("name")))
                .findFirst()
                .orElseThrow(() -> new ServiceError("Role not found"))
                .get("id").toString();
    }



    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}



