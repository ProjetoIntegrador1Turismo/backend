package ifpr.roteiropromo.interestPoint.service;


import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.domain.entities.TouristPoint;
import ifpr.roteiropromo.core.interestPoint.repository.InterestPointRepository;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(InterestPointService.class)
public class InterestPointServiceTest {

    @Mock
    private InterestPointRepository interestPointRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    private InterestPointService interestPointService;






    @Test
    public void interestPointAlreadyExist_shouldReturnFalse() {
        InterestPointDTOForm interestPointDTOForm = new InterestPointDTOForm();
        interestPointDTOForm.setName("Teste");
        Assertions.assertFalse(interestPointService.interestPointAlreadyExist(interestPointDTOForm.getName()));
    }

    @Test
    public void interestPointAlreadyExist_shouldReturnTrue() {
        InterestPointDTOForm interestPointDTOForm = new InterestPointDTOForm();
        interestPointDTOForm.setName("Cataratas do Iguaçu");
        TouristPoint touristPointFound = new TouristPoint();
        touristPointFound.setName("Cataratas do Iguaçu");
        when(interestPointRepository.getOnByName(any())).thenReturn(touristPointFound);
        Assertions.assertTrue(interestPointService.interestPointAlreadyExist(interestPointDTOForm.getName()));
    }

}
