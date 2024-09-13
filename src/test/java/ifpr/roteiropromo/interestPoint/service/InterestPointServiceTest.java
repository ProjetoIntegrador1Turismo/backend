package ifpr.roteiropromo.interestPoint.service;


import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.domain.entities.TouristPoint;
import ifpr.roteiropromo.core.interestPoint.repository.InterestPointRepository;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(InterestPointService.class)
@Log4j2
public class InterestPointServiceTest {

    @Mock
    private InterestPointRepository interestPointRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    private InterestPointService interestPointService;


    @Test
    public void create_shouldSetDefaultImageCoverUrl() {
        when(interestPointRepository.getOnByName(any())).thenReturn(null);
        InterestPointDTOForm interestPointDTOForm = new InterestPointDTOForm();
        interestPointDTOForm.setName("Cataratas do Iguaçu");
        interestPointDTOForm.setInterestPointType("TOURIST_POINT");
        interestPointDTOForm.setImageCoverUrl(null);

        TouristPoint touristPoint = new TouristPoint();
        touristPoint.setImageCoverUrl("http://localhost:8081/uploads/interestpointplaceholder.webp");

        when(modelMapper.map(any(), any())).thenAnswer(invocation -> {
            InterestPointDTOForm dto = invocation.getArgument(0);
            TouristPoint mappedPoint = new TouristPoint();
            mappedPoint.setName(dto.getName());
            mappedPoint.setImageCoverUrl(dto.getImageCoverUrl());
            return mappedPoint;
        });

        when(interestPointRepository.save(any())).thenReturn(touristPoint);

        InterestPoint interestPoint = interestPointService.create(interestPointDTOForm);
        Assertions.assertEquals("http://localhost:8081/uploads/interestpointplaceholder.webp", interestPoint.getImageCoverUrl());
    }


    @Test
    public void create_shouldThrowExceptionWhenInterestPointAlreadyExist() {
        InterestPointDTOForm interestPointDTOForm = new InterestPointDTOForm();
        interestPointDTOForm.setName("Cataratas do Iguaçu");
        interestPointDTOForm.setInterestPointType("TOURIST_POINT");
        TouristPoint touristPointFound = new TouristPoint();
        touristPointFound.setName("Cataratas do Iguaçu");
        when(interestPointRepository.getOnByName(any())).thenReturn(touristPointFound);
        Throwable exception = Assertions.assertThrows(ServiceError.class, () -> interestPointService.create(interestPointDTOForm));
        Assertions.assertEquals("There is already a point of interest with that name: Cataratas do Iguaçu", exception.getMessage());
    }

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
