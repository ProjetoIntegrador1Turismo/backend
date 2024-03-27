package ifpr.roteiropromo.core.auth;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthenticationController {

    //Busca no keycloak o token do usuário já cadastrado (uma forma de fazer pelo back-end a captura do token do usuário)
    @PostMapping()
    public ResponseEntity<String> getToken(@RequestBody UserAuthenticationDTO user){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> userDataForm = new LinkedMultiValueMap<>();
        userDataForm.add("client_id", user.getClientId());
        userDataForm.add("username", user.getUsername());
        userDataForm.add("password", user.getPassword());
        userDataForm.add("grant_type", user.getGrantType());
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> entityToRequestKeycloak = new HttpEntity<>(userDataForm, httpHeaders);
        return restTemplate.postForEntity(
                "http://localhost:8080/realms/SpringBootKeycloak/protocol/openid-connect/token",
                entityToRequestKeycloak, String.class);
    }






}
