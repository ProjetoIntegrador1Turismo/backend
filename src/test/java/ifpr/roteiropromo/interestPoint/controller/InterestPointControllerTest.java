package ifpr.roteiropromo.interestPoint.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.controller.InterestPointController;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.domain.entities.TouristPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = InterestPointController.class, excludeAutoConfiguration = OAuth2ClientAutoConfiguration.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class InterestPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    InterestPointService interestPointService;

    ModelMapper mapper;
    InterestPoint interestPoint;
    InterestPointDTOForm payload;

    @BeforeEach
    public void setup(){

        mapper = new ModelMapper();

        payload = new InterestPointDTOForm();
        payload.setName("Cataratas do Iguaçu");
        payload.setInterestPointType("TOURIST_POINT");
        payload.setShortDescription("Lorem ipsum lorem.");
        payload.setDuration("2 horas");
        payload.setAverageValue(4);
        payload.setLongDescription("Lorem ipsum lorem, lorem lorem ipsum lorem.");

        interestPoint = mapper.map(payload, TouristPoint.class);
        interestPoint.setId(1L);
    }



    @Test
    public void createNewInterestPoint_shouldReturnInterestPointCreated() throws Exception {
        given(interestPointService.create(ArgumentMatchers.any())).willReturn(interestPoint);

        ResultActions actions = mockMvc.perform(post("/interestpoint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload))
                );

        actions.andDo(print())
            .andExpectAll(
            status().isOk(),
            jsonPath("$.name").value(payload.getName())
        );
    }

    @Test
    public void createNewInterestPoint_shouldReturnBadRequest() throws Exception {
        given(interestPointService.create(ArgumentMatchers.any())).willThrow(
                new ServiceError("There is already a point of interest with that name: Cataratas do Iguaçu")
        );

        ResultActions actions = mockMvc.perform(post("/interestpoint")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload))
        );

        actions.andDo(print())
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    @Test
    public void getOneById_shouldReturnOneInterestPoint() throws Exception {
        given(interestPointService.getOne(ArgumentMatchers.any())).willReturn(interestPoint);

        ResultActions actions = mockMvc.perform(get("/interestpoint/1")
                .contentType(MediaType.APPLICATION_JSON)
        );

        actions.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.name").value(payload.getName())
                );
    }

    @Test
    public void getOneById_shouldReturnBadRequest() throws Exception {
        given(interestPointService.getOne(ArgumentMatchers.any())).willThrow(
                new ServiceError("Não foi possível encontrar um ponto de interesse com o ID: 1"));

        ResultActions actions = mockMvc.perform(get("/interestpoint/1")
                .contentType(MediaType.APPLICATION_JSON)
        );

        actions.andDo(print())
                .andExpectAll(
                        status().isBadRequest()
                );
    }









}
