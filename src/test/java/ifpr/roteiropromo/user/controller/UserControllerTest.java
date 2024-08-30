package ifpr.roteiropromo.user.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import ifpr.roteiropromo.core.user.controller.UserController;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTO;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTOForm;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = UserController.class, excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false) //remove security exception
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    private UserDTOForm userDTOForm;
    private UserDTO userDTO;

    private Tourist tourist;

    private List<User> userList;

    @BeforeEach
    public void setup(){

        userDTOForm = new UserDTOForm();
        userDTOForm.setFirstName("Carlos");
        userDTOForm.setLastName("Alberto");
        userDTOForm.setEmail("praca@gmail.com");
        userDTOForm.setPassword("123abc");

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName(userDTOForm.getEmail());
        userDTO.setFirstName(userDTOForm.getFirstName());
        userDTO.setLastName(userDTOForm.getLastName());
        userDTO.setEmail(userDTOForm.getEmail());

        userList = new ArrayList<>();
        tourist = new Tourist();
        tourist.setId(1L);
        tourist.setFirstName("Carlos");
        tourist.setLastName("Alberto");
        tourist.setEmail("praca@gmail.com");
        userList.add(tourist);

    }

    @Test
    public void createNewUser_mustReturnUserDTO() throws Exception {

        given(userService.creatNewUser(ArgumentMatchers.any())).willReturn(userDTO);

        ResultActions actions = mockMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTOForm))
        );
        actions.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(userDTO.getId()),
                        jsonPath("$.email").value(userDTO.getEmail())
                );
    }

//    @Test
//    public void getAllUsers_mustReturnUserList() throws Exception {
//        given(userService.getAll()).willReturn(userList);
//
//        ResultActions actions = mockMvc.perform(get("/user"));
//        actions.andDo(print())
//                .andExpectAll(
//                        status().isOk(),
//                        jsonPath("$", hasSize(1))
//                );
//    }

    @Test
    public void updateUser_mustReturnUpdatedUser() throws Exception{
        UserDTO userDataToUpdate = new UserDTO();
        userDataToUpdate.setFirstName("Pedro");
        userDataToUpdate.setLastName("Silva");
        tourist.setFirstName(userDataToUpdate.getFirstName());
        tourist.setLastName(userDataToUpdate.getLastName());
        given(userService.update(ArgumentMatchers.any())).willReturn(tourist);

        ResultActions actions = mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDataToUpdate))
        );

        actions.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.firstName").value(userDataToUpdate.getFirstName()),
                        jsonPath("$.lastName").value(userDataToUpdate.getLastName())
                );

    }




}
