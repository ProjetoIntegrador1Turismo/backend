package ifpr.roteiropromo.core.utils;

import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.errors.ServiceError;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.util.Base64;
import java.util.Map;


@Log4j2
@Service
@RequiredArgsConstructor
public class JwtTokenHandler {

    private final String keycloackClientID = System.getProperty("CLIENT_ID");
    private final String keycloackAdminUser = System.getProperty("KC_ADMIN_NAME");
    private final String keycloackAdminPassword = System.getProperty("KC_ADMIN_PASSWORD");


    public AuthenticatedUserDTO getUserDataFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
            AuthenticatedUserDTO userDTO = new AuthenticatedUserDTO();
            //Extrai o token
            String token = jwtAuthenticationToken.getToken().getTokenValue();

            //Extrai os dados do token
            String idKeyclock = jwtAuthenticationToken.getName();
            Object userEmail = jwtAuthenticationToken.getTokenAttributes().get("preferred_username");
            Object userName = jwtAuthenticationToken.getTokenAttributes().get("given_name");
            Object userLastName = jwtAuthenticationToken.getTokenAttributes().get("family_name");
            log.info("Token JWT: " + token);
            log.info("Email: " + userEmail);
            log.info("User id keycloack: " + idKeyclock);
            log.info("User name: " + userName);
            log.info("User last name: " + userLastName);
            userDTO.setEmail(userEmail.toString());
            userDTO.setFirstName(userName.toString());
            userDTO.setLastName(userLastName.toString());

            return userDTO;
        } else {
            throw new ServiceError("User not authenticated");
        }
    }


    public String getAdminToken(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://"+System.getProperty("KEYCLOAK_URL")+"/realms/master/protocol/openid-connect/token",
                createEntityRequestForAdmin(), Map.class);
        return (String) response.getBody().get("access_token");
    }


    private HttpEntity<MultiValueMap<String, String>> createEntityRequestForAdmin(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> userDataForm = new LinkedMultiValueMap<>();
        userDataForm.add("client_id", this.keycloackClientID);
        userDataForm.add("username", this.keycloackAdminUser);
        userDataForm.add("password", this.keycloackAdminPassword);
        userDataForm.add("grant_type", "password");
        HttpEntity<MultiValueMap<String, String>> entityToRequestKeycloak = new HttpEntity<>(userDataForm, httpHeaders);
        return entityToRequestKeycloak;
    }

    public String getUserEmailFromToken(String authToken) {
        String[] parts = authToken.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Token JWT inválido");
        }
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        JSONObject jsonObject = new JSONObject(payload);
        return jsonObject.getString("preferred_username");
    }

}
