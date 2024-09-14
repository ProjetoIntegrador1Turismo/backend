package ifpr.roteiropromo.interestPoint.service;


import ifpr.roteiropromo.core.enums.InterestPointType;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.entities.TouristPoint;
import ifpr.roteiropromo.core.interestPoint.repository.InterestPointRepository;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    public void create_shouldCreateOneTouristPoint() {
        InterestPointDTOForm interestPointDTOForm = createTouristPoint();
        when(interestPointRepository.getOnByName(any())).thenReturn(null);
        when(modelMapper.map(any(), eq(TouristPoint.class))).thenAnswer(invocation -> {
            InterestPointDTOForm dto = invocation.getArgument(0);
            TouristPoint mappedPoint = new TouristPoint();
            mappedPoint.setName(dto.getName());
            mappedPoint.setImageCoverUrl(dto.getImageCoverUrl());
            mappedPoint.setInterestPointType(InterestPointType.valueOf(dto.getInterestPointType()));
            return mappedPoint;
        });
        when(interestPointRepository.save(any())).thenReturn(new TouristPoint());

        interestPointService.create(interestPointDTOForm);

        ArgumentCaptor<TouristPoint> captor = ArgumentCaptor.forClass(TouristPoint.class);
        verify(interestPointRepository).save(captor.capture());
        TouristPoint savedInterestPoint = captor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(interestPointDTOForm.getName(), savedInterestPoint.getName()),
                () -> Assertions.assertEquals(interestPointDTOForm.getInterestPointType(),
                        savedInterestPoint.getInterestPointType().toString())
        );
    }

    @Test
    public void create_shouldSetDefaultImageCoverUrl() {
        when(interestPointRepository.getOnByName(any())).thenReturn(null);
        InterestPointDTOForm interestPointDTOForm = new InterestPointDTOForm();
        interestPointDTOForm.setName("Cataratas do Iguaçu");
        interestPointDTOForm.setInterestPointType("TOURIST_POINT");

        TouristPoint touristPoint = new TouristPoint();

        when(modelMapper.map(any(), any())).thenAnswer(invocation -> {
            InterestPointDTOForm dto = invocation.getArgument(0);
            TouristPoint mappedPoint = new TouristPoint();
            mappedPoint.setName(dto.getName());
            mappedPoint.setImageCoverUrl(dto.getImageCoverUrl());
            return mappedPoint;
        });

        ArgumentCaptor<TouristPoint> captor = ArgumentCaptor.forClass(TouristPoint.class);
        when(interestPointRepository.save(any())).thenReturn(touristPoint);
        interestPointService.create(interestPointDTOForm);
        verify(interestPointRepository).save(captor.capture());
        TouristPoint savedInterestPoint = captor.getValue();

        Assertions.assertEquals("http://localhost:8081/uploads/interestpointplaceholder.webp", savedInterestPoint.getImageCoverUrl());
    }


    @Test
    public void create_shouldThrowExceptionWhenInterestPointAlreadyExist() {
        InterestPointDTOForm interestPointDTOForm = new InterestPointDTOForm();
        interestPointDTOForm.setName("Cataratas do Iguaçu");
        interestPointDTOForm.setInterestPointType("TOURIST_POINT");
        when(interestPointRepository.getOnByName(any())).thenReturn(new TouristPoint());
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
        when(interestPointRepository.getOnByName(any())).thenReturn(new TouristPoint());
        Assertions.assertTrue(interestPointService.interestPointAlreadyExist(interestPointDTOForm.getName()));
    }


    public InterestPointDTOForm createTouristPoint(){
        InterestPointDTOForm interestPointDTOForm = new InterestPointDTOForm();
        interestPointDTOForm.setName("Cataratas do Iguaçu");
        interestPointDTOForm.setAverageValue(3);
        interestPointDTOForm.setShortDescription("Cataratas do Iguaçu é uma das mais belas quedas d'água do mundo.");
        interestPointDTOForm.setImageCoverUrl(null);
        interestPointDTOForm.setLongDescription("As Cataratas do Iguaçu são um conjunto de cerca de 275 quedas de água no rio Iguaçu, localizadas entre o Parque Nacional do Iguaçu, Paraná, no Brasil, e o Parque Nacional Iguazú em Misiones, na Argentina.");
        interestPointDTOForm.setDuration("2 horas");
        interestPointDTOForm.setInterestPointType("TOURIST_POINT");
        return interestPointDTOForm;
    }


}
