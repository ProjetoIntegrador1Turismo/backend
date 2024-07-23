package ifpr.roteiropromo.core.utils;

import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.errors.ServiceError;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

import java.util.Map;


@Log4j2
@Service
@RequiredArgsConstructor
public class JwtTokenHandler {


    //Extrai os dados do usuario a partir do token enviado na requisição
    public AuthenticatedUserDTO getUserDataFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se a autenticação é do tipo JWT (se o usuário estiver autenticado com um token JWT)
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
            AuthenticatedUserDTO userDTO = new AuthenticatedUserDTO();
            //Extrai o token
            String token = jwtAuthenticationToken.getToken().getTokenValue();

            //Extrai os dados do token

            // 23/07/2023 BKP:
//            String idKeyclock = jwtAuthenticationToken.getName();
//            Object userEmail = jwtAuthenticationToken.getTokenAttributes().get("email");
//            Object userName = jwtAuthenticationToken.getTokenAttributes().get("given_name");
//            log.info("Token JWT: " + token);
//            log.info("Email: " + userEmail);
//            log.info("User id keycloack: " + idKeyclock);
//            log.info("User name: " + userName);
//            userDTO.setEmail(userEmail.toString());
//            userDTO.setFirstName(userName.toString());

            // ATUALIZADO EM 23/07/2023 PARA PEGAR O E-MAIL A PARTIR DO CAMPO "PREFERED USERNAME" JÁ QUE NÃO TEM MAIS O CAMPO EMAIL NO KC.
            String idKeyclock = jwtAuthenticationToken.getName();
            Object userEmail = jwtAuthenticationToken.getTokenAttributes().get("preferred_username"); //ATUALIZADO AQUI
            Object userName = jwtAuthenticationToken.getTokenAttributes().get("given_name");
            log.info("Token JWT: " + token);
            log.info("Email: " + userEmail);
            log.info("User id keycloack: " + idKeyclock);
            log.info("User name: " + userName);
            userDTO.setEmail(userEmail.toString());
            userDTO.setFirstName(userName.toString());

            return userDTO;
        } else {
            throw new ServiceError("User not authenticated");
        }
    }


    public String getAdminToken(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:8080/realms/master/protocol/openid-connect/token",
                createEntityRequestForAdmin(), Map.class);
        return (String) response.getBody().get("access_token");
    }


    private HttpEntity<MultiValueMap<String, String>> createEntityRequestForAdmin(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> userDataForm = new LinkedMultiValueMap<>();
        userDataForm.add("client_id", "admin-cli");
        userDataForm.add("username", "admin");
        userDataForm.add("password", "anderson");
        userDataForm.add("grant_type", "password");
        HttpEntity<MultiValueMap<String, String>> entityToRequestKeycloak = new HttpEntity<>(userDataForm, httpHeaders);
        return entityToRequestKeycloak;
    }

}
