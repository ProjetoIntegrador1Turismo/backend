package ifpr.roteiropromo.core.auth.service;

import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.auth.domain.UserAuthenticationDTO;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.user.domain.entities.Admin;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationService {

    private final UserService userService;


    //Retorna os dados do usuario e o token de autenticação caso exista
    //Caso não exista, ou, os dados de login estejam errados, lança uma exceção
    public AuthenticatedUserDTO getUSerTokenAndData(UserAuthenticationDTO user) {
        if (!userExists(user.getUsername())) {
            throw new ServiceError("No user found with this email: " + user.getUsername());
        }
        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> userDataForm = new LinkedMultiValueMap<>();
            userDataForm.add("client_id", "login-app");
            userDataForm.add("username", user.getUsername());
            userDataForm.add("password", user.getPassword());
            userDataForm.add("grant_type", "password");
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> entityToRequestKeycloak = new HttpEntity<>(userDataForm, httpHeaders);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "http://localhost:8080/realms/SpringBootKeycloak/protocol/openid-connect/token",
                    entityToRequestKeycloak, Map.class);
            AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO();
            User userEntity = userService.getOneByEmail(user.getUsername());
            authenticatedUserDTO.setAuthToken((String) response.getBody().get("access_token"));
            authenticatedUserDTO.setFirstName(userEntity.getFirstName());
            authenticatedUserDTO.setEmail(userEntity.getEmail());
            if (userEntity instanceof Tourist){
                authenticatedUserDTO.setUserType("Tourist");
            }
            if (userEntity instanceof Admin){
                authenticatedUserDTO.setUserType("Admin");
            }
            else{
                authenticatedUserDTO.setUserType("Guide");
            }
            return authenticatedUserDTO;

        }catch (Exception e){
            throw new ServiceError("Invalid username or password.");
        }
    }



    private Boolean userExists(String email){
        return userService.existsUserByEmail(email);
    }


}
