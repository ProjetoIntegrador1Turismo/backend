package ifpr.roteiropromo.auth.service;

import ifpr.roteiropromo.core.auth.domain.UserAuthenticationDTO;
import ifpr.roteiropromo.core.auth.service.AuthenticationService;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.user.service.UserService;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(AuthenticationService.class)
public class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenHandler jwtTokenHandler;

    @InjectMocks
    private AuthenticationService authenticationService;

    private UserAuthenticationDTO userAuthenticationDTO;

    @Before
    public void setup(){

        userAuthenticationDTO = new UserAuthenticationDTO();
        userAuthenticationDTO.setUsername("useremail@email.com");
        userAuthenticationDTO.setPassword("12345");

    }

    @Test
    public void getUSerTokenAndData_shouldThrowNoUserFoundError(){
        when(userService.existsUserByEmail(any())).thenReturn(true);

        Assertions.assertThrows(ServiceError.class, () -> authenticationService.getUSerTokenAndData(userAuthenticationDTO));
    }


}
