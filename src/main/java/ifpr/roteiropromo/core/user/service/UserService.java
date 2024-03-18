package ifpr.roteiropromo.core.user.service;


import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTO;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTOForm;
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
                    createRequestToKeycloack(userDTOForm), Map.class);
            log.info(response.getStatusCode());
        }catch (Exception e){
            throw  new ServiceError("E-mail already registered!");
        }
        if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(201))){
            log.info("criar um novo usuário no banco local com os mesmos dados, verificando se é guia");
            User newUser = mapper.map(userDTOForm, User.class);
            return mapper.map(userRepository.save(newUser), UserDTO.class);
        }else {
            throw new ServiceError("An error occurred when creating the new user.");
        }

    }


    private HttpEntity<String> createRequestToKeycloack(UserDTOForm userDTOForm){

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
                userDTOForm.getUsername(), true, true, userDTOForm.getFirstName(), userDTOForm.getLastName(), userDTOForm.getEmail(), userDTOForm.getPassword());
        return new HttpEntity<String>(userJson, httpHeaders);

    }


    public List<UserDTO> getAll() {
        return mapList(userRepository.findAll(), UserDTO.class);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
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