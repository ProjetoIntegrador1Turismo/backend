package ifpr.roteiropromo.user.service;


import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.repository.UserRepository;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Log4j2
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UserService.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private Tourist tourist;
    private Guide guide;

    @InjectMocks
    private UserService userService;

    @Before
    public void setup(){
        tourist = new Tourist();
        tourist.setId(1L);
        tourist.setFirstName("gepeto");
        tourist.setLastName("pinóquio");
        tourist.setUserName("titeriteiro");
        tourist.setEmail("titeriteiro@gmail.com");

        guide = new Guide();
        guide.setFirstName("ronaldo");
        guide.setUserName("fenomeno");
        guide.setId(2L);
        guide.setEmail("loumar@turismo");
        guide.setIsApproved(true);
        guide.setLastName("nazario");
        guide.setCadasturCode("123456789");

    }

    @Test
    public void getOneByEmail_mustReturnTourist(){
        when(userRepository.getOneByEmail(any())).thenReturn(tourist);
        User userFound = userService.getOneByEmail("titeriteiro@gmail.com");

        Assertions.assertNotNull(userFound);
        Assertions.assertTrue(userFound instanceof Tourist);
    }

    @Test
    public void getOneByEmail_mustReturnGuide(){
        when(userRepository.getOneByEmail(any())).thenReturn(guide);
        User userFound = userService.getOneByEmail("loumar@turismo");

        Assertions.assertNotNull(userFound);
        Assertions.assertTrue(userFound instanceof Guide);
    }

    @Test
    public void getOneByEmail_mustThrowServiceErrorWhenNotFoundUser() {
        when(userRepository.getOneByEmail(any())).thenReturn(null);
        Assertions.assertThrows(ServiceError.class, () -> {userService.getOneByEmail("inexiste@gmail.com");});
    }

    @Test
    public void findById_mustThrowServiceErrorWhenNotFoundUser(){
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ServiceError.class, () -> {userService.findById(1L);});
    }

    @Test
    public void getTouristById_mustReturnTourist(){
        when(userRepository.findById(any())).thenReturn(Optional.of(tourist));
        Assertions.assertNotNull(userService.getTouristById(1L));
    }

    @Test
    public void getTouristById_mustThrowServiceErrorWhenUserIsNotTouristType(){
        when(userRepository.findById(any())).thenReturn(Optional.of(guide));
        Throwable exception = Assertions.assertThrows(ServiceError.class, () -> {userService.getTouristById(2L);});
        Assertions.assertEquals("User found with id: 2 is not a tourist so he can't make comments!", exception.getMessage());
    }



}
