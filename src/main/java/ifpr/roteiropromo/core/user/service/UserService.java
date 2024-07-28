package ifpr.roteiropromo.core.user.service;


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
        ResponseEntity<Map> response;

        // Verificando se o usuário já não é cadastrado:
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.postForEntity(
                    "http://localhost:8080/admin/realms/SpringBootKeycloak/users",
                    createRequestToKeycloakToNewUser(userDTOForm), Map.class);
        } catch (HttpClientErrorException e) {
            throw new ServiceError("E-mail already registered!");
        } catch (Exception e) {
            throw new ServiceError("An unexpected error occurred: " + e.getMessage());
        }

        // Verificando de que tipo é o User que será cadastrado:
        if (response.getStatusCode().equals(HttpStatus.CREATED)) {
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

                // Adicionando role USER no keycloak:
                try {
                    String userId = getUserIdFromKeycloak(user);
                    this.addRoleToUser(userId, jwtTokenHandler.getAdminToken(), "USER");
                } catch (Exception e) {
                    throw new ServiceError("Failed to assign role to user: " + e.getMessage());
                }
            } else {
                Guide guide = mapper.map(userDTOForm, Guide.class);
                guide.setUserName(userDTOForm.getFirstName());
                guide.setIsApproved(false);
                user = guide;
            }


            // Salvando no banco
            User savedUser = userRepository.save(user);


            return mapper.map(savedUser, UserDTO.class);

        } else {
            throw new ServiceError("An error occurred when creating the new user. Status: " + response.getStatusCode());
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


    public Tourist updateTourist(Tourist touristFound) {
        return userRepository.save(touristFound);
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

    // get all guides dto
//    public List<GuideDTO> getAllGuides() {
//        return userRepository.findAll().stream()
//                .filter(user -> user instanceof Guide)
//                .map(user -> mapper.map(user, GuideDTO.class))
//                .collect(Collectors.toList());
//    }

    public List<Guide> getAllGuides() {
        return userRepository.findAllGuides();
    }


    public List<GuideDTO> getAllGuidesWithReviews() {
        List<Guide> guides = userRepository.findAllGuides();
        return guides.stream()
                .map(guide -> {
                    GuideDTO guideDTO = mapper.map(guide, GuideDTO.class);
                    List<Review> reviews = reviewRepository.findByGuideId(guide.getId());
                    List<ReviewDTO> reviewDTOs = mapList(reviews, ReviewDTO.class);
                    guideDTO.setReviews(reviewDTOs);
                    return guideDTO;
                })
                .collect(Collectors.toList());
    }


    public GuideDTO getGuideById(Long id) {
        Guide guide = (Guide) userRepository.findById(id).orElseThrow(() -> new ServiceError("Guide not found with id: " + id));
        return mapper.map(guide, GuideDTO.class);
    }


    public List<GuideDTO> getAllUnapprovedGuides() {
        List<Guide> guides = userRepository.findAllUnapprovedGuides();
        return guides.stream()
                .map(guide -> modelMapper.map(guide, GuideDTO.class))
                .collect(Collectors.toList());
    }


    public List<Review> getReviewsByGuideId(Long guideId) {
        return reviewRepository.findByGuideId(guideId);
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

    // ** RATINGS: ** //

    public GuideDTO rateGuide(RatingDTO ratingDTO) {
        Guide guide = (Guide) userRepository.getOneByEmail(ratingDTO.getGuideEmail());
        if (guide == null) {
            throw new ServiceError("Guide not found with email: " + ratingDTO.getGuideEmail());
        }

        Tourist tourist = (Tourist) userRepository.getOneByEmail(ratingDTO.getUserEmail());
        if (tourist == null) {
            throw new ServiceError("Tourist not found with email: " + ratingDTO.getUserEmail());
        }

        if (ratingDTO.getRating() < 1 || ratingDTO.getRating() > 5) {
            throw new ServiceError("Rating must be between 1 and 5.");
        }

        boolean alreadyReviewed = reviewRepository.existsByGuideIdAndTourist(guide.getId(), tourist);
        if (alreadyReviewed) {
            throw new ServiceError("User has already rated this guide.");
        }

        Review review = new Review();
        review.setText(ratingDTO.getText());
        review.setDate(ratingDTO.getDate());
        review.setRating(ratingDTO.getRating());
        review.setGuideId(guide.getId());
        review.setTourist(tourist);

        reviewRepository.save(review);

        return mapper.map(guide, GuideDTO.class);
    }


    public GuideDTO updateRating(RatingDTO ratingDTO) {
        Guide guide = (Guide) userRepository.getOneByEmail(ratingDTO.getGuideEmail());
        if (guide == null) {
            throw new ServiceError("Guide not found with email: " + ratingDTO.getGuideEmail());
        }

        Tourist tourist = (Tourist) userRepository.getOneByEmail(ratingDTO.getUserEmail());
        if (tourist == null) {
            throw new ServiceError("Tourist not found with email: " + ratingDTO.getUserEmail());
        }

        if (ratingDTO.getRating() < 1 || ratingDTO.getRating() > 5) {
            throw new ServiceError("Rating must be between 1 and 5.");
        }

        Review existingReview = reviewRepository.findByGuideIdAndTourist(guide.getId(), tourist)
                .orElseThrow(() -> new ServiceError("User has not rated this guide yet."));

        if (ratingDTO.getText() != null && !ratingDTO.getText().isEmpty()) {
            existingReview.setText(ratingDTO.getText());
        }

        if (ratingDTO.getDate() != null && !ratingDTO.getDate().isEmpty()) {
            existingReview.setDate(ratingDTO.getDate());
        }

        if (ratingDTO.getRating() != null) {
            existingReview.setRating(ratingDTO.getRating());
        }

        reviewRepository.save(existingReview);

        return modelMapper.map(guide, GuideDTO.class);
    }


    public List<GuideDTO> getTopRatedGuides(int topN) {
        List<Guide> guides = userRepository.findAll().stream()
                .filter(user -> user instanceof Guide)
                .map(user -> (Guide) user)
                .collect(Collectors.toList());

        Map<Guide, Double> guideAverageRatings = new HashMap<>();
        for (Guide guide : guides) {
            List<Review> reviews = reviewRepository.findByGuideId(guide.getId());
            double averageRating = guide.getAverageRating(reviews);
            guideAverageRatings.put(guide, averageRating);
        }

        return guideAverageRatings.entrySet().stream()
                .sorted(Map.Entry.<Guide, Double>comparingByValue().reversed())
                .limit(topN)
                .map(entry -> mapper.map(entry.getKey(), GuideDTO.class))
                .collect(Collectors.toList());
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



