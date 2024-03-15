package ifpr.roteiropromo.core.user.service;


import ifpr.roteiropromo.core.user.domain.User;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {


    private final JwtTokenHandler jwtTokenHandler;

    public void creatNewUser(User user){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:8080/admin/realms/SpringBootKeycloak/users",
                createRequestToKeycloack(user), Map.class);
        log.info(response);


    }


    private HttpEntity<String> createRequestToKeycloack(User user){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(jwtTokenHandler.getAdminToken());
        String userJson = String.format(
                "{" +
                        "\"username\": \"%s\"," +
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
                user.getUsername(), true, true, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        return new HttpEntity<String>(userJson, httpHeaders);

    }





}

/*
HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(jwtTokenHandler.getAdminToken());

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", user.getUsername());
        requestBody.add("enabled", "true");
        requestBody.add("emailVerified", "true");
        requestBody.add("firstName", user.getFirstName());
        requestBody.add("lastName", user.getLastName());
        requestBody.add("email", user.getEmail());

        MultiValueMap<String, String> credentials = new LinkedMultiValueMap<>();
        credentials.add("type", "password");
        credentials.add("value", user.getPassword());
        credentials.add("temporary", "false");

        requestBody.add("credentials", credentials);

        return new HttpEntity<MultiValueMap<String, Object>>(requestBody, httpHeaders);*/