package ifpr.roteiropromo.auth;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ifpr.roteiropromo.core.auth.controller.AuthenticationController;
import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.auth.domain.UserAuthenticationDTO;
import ifpr.roteiropromo.core.auth.service.AuthenticationService;
import ifpr.roteiropromo.core.errors.AuthenticationServerError;
import ifpr.roteiropromo.core.errors.ServiceError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthenticationController.class, excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AuthenticationService authenticationService;

    private UserAuthenticationDTO userAuthForm;
    private AuthenticatedUserDTO authenticatedUserDTO;

    @BeforeEach
    public void setup(){

        userAuthForm = new UserAuthenticationDTO();
        userAuthForm.setUsername("user@gmail.com");
        userAuthForm.setPassword("12345");

        authenticatedUserDTO = new AuthenticatedUserDTO();

        authenticatedUserDTO.setEmail(userAuthForm.getUsername());
        authenticatedUserDTO.setFirstName("Bill");
        authenticatedUserDTO.setLastName("Kid");
        authenticatedUserDTO.setUserType("Guide");
        authenticatedUserDTO.setAuthToken("testTokenData");
        authenticatedUserDTO.setAuthTokenExpiresIn("testTokenTime");
        authenticatedUserDTO.setRefreshTokenExpiresIn("refreshTokenData");
        authenticatedUserDTO.setRefreshTokenExpiresIn("testRefreshTime");
        authenticatedUserDTO.setCadasturCode("Tur4655");
        authenticatedUserDTO.setProfileImageUrl("testUrlImg");

    }



    @Test
    public void getUSerTokenAndData_shouldRespondBadRequest() throws Exception {
        given(authenticationService.getUSerTokenAndData(ArgumentMatchers.any())).willThrow(
                new ServiceError("No user found with this email: " + userAuthForm.getUsername())
        );

        ResultActions actions = mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userAuthForm))
        );

        actions.andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").value("No user found with this email: " + userAuthForm.getUsername())
                );

    }


}
