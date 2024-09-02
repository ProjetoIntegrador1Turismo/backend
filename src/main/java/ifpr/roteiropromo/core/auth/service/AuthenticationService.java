package ifpr.roteiropromo.core.auth.service;

import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.auth.domain.UserAuthenticationDTO;
import ifpr.roteiropromo.core.errors.AuthenticationServerError;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.user.domain.entities.Admin;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.service.UserService;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationService {

    private final UserService userService;
    private final ModelMapper mapper;
    private final JwtTokenHandler jwtTokenHandler;


    //Retorna os dados do usuario e o token de autenticação caso exista
    //Caso não exista, ou, os dados de login estejam errados, lança uma exceção
    public AuthenticatedUserDTO getUSerTokenAndData(UserAuthenticationDTO user) {
        if (!userExists(user.getUsername())) {
            throw new ServiceError("No user found with this email: " + user.getUsername());
        }

        Map<String,String> userTokenData = validateUserLoginDataAndGetToken(user);

        if(guideIsNotApproved(user.getUsername())){
            throw new AuthenticationServerError("Guide not approved yet", HttpStatus.UNAUTHORIZED);
        }

        AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO();
        User userEntity = userService.getOneByEmail(user.getUsername());
        mapper.map(userEntity, authenticatedUserDTO);
        authenticatedUserDTO.setAuthToken(userTokenData.get("authToken"));
        authenticatedUserDTO.setUserType(getUserType(userEntity));
        authenticatedUserDTO.setRefreshToken(userTokenData.get("refreshToken"));
        authenticatedUserDTO.setAuthTokenExpiresIn(userTokenData.get("authTokenExpiresIn"));
        authenticatedUserDTO.setRefreshTokenExpiresIn(userTokenData.get("refreshTokenExpiresIn"));
        return authenticatedUserDTO;
    }

    private boolean guideIsNotApproved(String username) {
        User user = userService.getOneByEmail(username);
        if(getUserType(user).equals("Tourist") || getUserType(user).equals("Admin") ){
            return false;
        }else{
            Guide guide = mapper.map(user, Guide.class);
            return !guide.getIsApproved();
        }
    }

    private String getUserType(User user){
        if (user instanceof Tourist) {
           return "Tourist";
        } else if (user instanceof Admin) {
            return "Admin";
        } else if (user instanceof Guide) {
            return "Guide";
        } else {
            return "Unknown";
        }
    }

    private Map<String, String> validateUserLoginDataAndGetToken(UserAuthenticationDTO user){
        try {
            HttpEntity<MultiValueMap<String, String>> entityToRequestKeycloak = new HttpEntity<>(buildFormRequestWithUserData(user), buildHeaderRequest());
            ResponseEntity<Map> response = makeRequestToAuthenticationServer(entityToRequestKeycloak);
            return getTokenDataFromResponse(response);
        } catch (Exception e) {
            throw new ServiceError("Invalid username or password.");
        }
    }

    private HttpHeaders buildHeaderRequest(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return httpHeaders;
    }

    private MultiValueMap<String, String> buildFormRequestWithUserData(UserAuthenticationDTO user){
        MultiValueMap<String, String> userDataForm = new LinkedMultiValueMap<>();
        userDataForm.add("client_id", "login-app");
        userDataForm.add("username", user.getUsername());
        userDataForm.add("password", user.getPassword());
        userDataForm.add("grant_type", "password");
        return userDataForm;
    }

    private Boolean userExists(String email){
        return userService.existsUserByEmail(email);
    }


    public AuthenticatedUserDTO getAuthRefreshToken(String refreshToken) {
        HttpEntity<MultiValueMap<String, String>> entityToRequestKeycloak = new HttpEntity<>(buildFormRequestWithRefreshToken(refreshToken), buildHeaderRequest());
        ResponseEntity<Map> response = makeRequestToAuthenticationServer(entityToRequestKeycloak);
        Map<String, String> tokenData = getTokenDataFromResponse(response);
        String emailUser = jwtTokenHandler.getUserEmailFromToken(tokenData.get("authToken"));
        User user = userService.getOneByEmail(emailUser);
        AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO();
        mapper.map(user, authenticatedUserDTO);
        authenticatedUserDTO.setUserType(getUserType(user));
        authenticatedUserDTO.setAuthToken(tokenData.get("authToken"));
        authenticatedUserDTO.setAuthTokenExpiresIn(tokenData.get("authTokenExpiresIn"));
        authenticatedUserDTO.setRefreshToken(tokenData.get("refreshToken"));
        authenticatedUserDTO.setRefreshTokenExpiresIn(tokenData.get("refreshTokenExpiresIn"));
        return authenticatedUserDTO;
    }

    private ResponseEntity<Map> makeRequestToAuthenticationServer(HttpEntity<MultiValueMap<String, String>> payLoad){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(
                "http://localhost:8080/realms/SpringBootKeycloak/protocol/openid-connect/token",
                payLoad, Map.class
        );
    }

    private MultiValueMap<String, String> buildFormRequestWithRefreshToken(String refreshToken) {
        MultiValueMap<String, String> userDataForm = new LinkedMultiValueMap<>();
        userDataForm.add("client_id", "login-app");
        userDataForm.add("refresh_token", refreshToken);
        userDataForm.add("grant_type", "refresh_token");
        return userDataForm;
    }

    private Map<String, String> getTokenDataFromResponse(ResponseEntity<Map> response){
        Map<String, String> tokenData = new HashMap<>();
        tokenData.put("authToken", response.getBody().get("access_token").toString());
        tokenData.put("authTokenExpiresIn", response.getBody().get("expires_in").toString());
        tokenData.put("refreshToken", response.getBody().get("refresh_token").toString());
        tokenData.put("refreshTokenExpiresIn", response.getBody().get("refresh_expires_in").toString());
        return tokenData;
    }

}
