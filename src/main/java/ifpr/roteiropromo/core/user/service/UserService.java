package ifpr.roteiropromo.core.user.service;


import ifpr.roteiropromo.core.errors.ResourceServerError;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTO;
import ifpr.roteiropromo.core.review.domain.entities.Review;
import ifpr.roteiropromo.core.review.repository.ReviewRepository;
import ifpr.roteiropromo.core.user.domain.dtos.*;
import ifpr.roteiropromo.core.user.domain.entities.Admin;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.domain.entities.User;
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
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;


    public UserDTO createNewUser(UserDTOForm userDTOForm) {
        //Primeiro: Cria - ou lança exceção - o usuario no servidor de autenticação
        createUserOnResourceServer(userDTOForm);

        // Verificando de que tipo é o User que será cadastrado:
        //MOVER O CRIAR ADMIN PARA UM METODO PRÓPRIO - Ñ DEIXAR NO MESMO METODO DE USUARIOS NORMAIS
        User user;
        if (userDTOForm.isActiveAdmin()) {
            Admin admin = mapper.map(userDTOForm, Admin.class);
            admin.setUserName(userDTOForm.getFirstName());
            admin.setActiveAdmin(true);
            user = admin;

            // Adicionando role ADMIN no keycloak:
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

        // Salvando no banco
        User savedUser = userRepository.save(user);
        return mapper.map(savedUser, UserDTO.class);
    }

    private void createUserOnResourceServer(UserDTOForm userDTOForm){
        try {
            new RestTemplate().postForEntity(
                    "http://localhost:8080/admin/realms/SpringBootKeycloak/users",
                    createRequestToKeycloakToNewUser(userDTOForm), Map.class);
        } catch (HttpClientErrorException.Conflict e) {
            throw new ResourceServerError("User e-mail already registered!", HttpStatus.CONFLICT);
        } catch (Exception e){
            throw new ResourceServerError("Error when try to access resource server.", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }


    // OBS: PARA UTILIZAR ESSE MÉTODO É PRECISO JÁ ESTAR COM O CONTEINER NOVO ATUALIZADO... (O COM TEMA DO APP)
    public String resetUserPassword(UserDTORecovery userDTORecovery) {
        try {
            User userFound = userRepository.getOneByEmail(userDTORecovery.getEmail());
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


    public void updateTourist(Tourist touristFound) {
        userRepository.save(touristFound);
    }


    public Guide createGuide(UserDTOForm userDTOForm) {
        if (userDTOForm.getCadasturCode() == null || userDTOForm.getCadasturCode().isEmpty()) {
            throw new ServiceError("CadasturCode é necessário para se cadastrar como guia!");
        }
        Guide guide = mapper.map(userDTOForm, Guide.class);
        guide.setUserName(userDTOForm.getFirstName());
        guide.setCadasturCode(userDTOForm.getCadasturCode());
        guide.setIsApproved(false);
        return userRepository.save(guide);
    }


    public UserDTO updateUser(UserDTOUpdate userDTOUpdate) {
        User user = userRepository.getOneByEmail(userDTOUpdate.getEmail());
        if (user == null) {
            throw new ServiceError("User not found with id: " + userDTOUpdate.getId());
        }

        // Atualizar nome no backend
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


    public void updateProfileImageUrl(Long id, String imageUrl) {
        User userFound = userRepository.findById(id).orElseThrow(() -> new ServiceError("User not found with id: " + id));
        userFound.setProfileImageUrl(imageUrl);
        userRepository.save(userFound);
    }

    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    // Métodos relacionados aos GUIDES:
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////

    public List<GuideDTO> getAllUnapprovedGuides() {
        List<Guide> guides = userRepository.findAllUnapprovedGuides();
        return guides.stream()
                .map(guide -> modelMapper.map(guide, GuideDTO.class))
                .collect(Collectors.toList());
    }

    public Guide approveGuide(Long id) {
        Guide guide = this.findGuideById(id);

        if (guide.getIsApproved()) {
            throw new ServiceError("Guide já está aprovado!");
        }

        // Adicionando role GUIDE no keycloak:
        try {
            String userId = getUserIdFromKeycloak(guide);
            this.addRoleToUser(userId, jwtTokenHandler.getAdminToken(), "GUIA");
        } catch (Exception e) {
            throw new ServiceError("Failed to assign role to user: " + e.getMessage());
        }

        guide.setIsApproved(true);
        userRepository.save(guide);

        return guide;
    }


    public Guide disapproveGuide(Long guideId) {
        Guide guide = findGuideById(guideId);
        if (guide == null){
            throw new ServiceError("Guide não encontrado.");
        }
        if (!guide.getIsApproved()){
            throw new ServiceError("Guide já está desativado!");
        }
        guide.setIsApproved(false);
        return userRepository.save(guide);
    }


    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    // Métodos relacionados ao KEYCLOAK:
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////


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

    private String getUserIdFromKeycloak(User user) {
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

        //
        // Atualizar nome no Keycloak
        //
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


    void addRoleToUser(String userId, String adminMasterToken, String roleName) {
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


    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    // Outros métodos:
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////


    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}



