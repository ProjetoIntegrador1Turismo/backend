package ifpr.roteiropromo.interestPoint.service;


import ifpr.roteiropromo.core.enums.InterestPointType;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.entities.*;
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
    public void create_shouldCreateOneRestaurant() {
        InterestPointDTOForm interestPointDTOForm = createInterestPoint("Lual nas Cataratas", "RESTAURANT");
        when(interestPointRepository.getOnByName(any())).thenReturn(null);
        when(modelMapper.map(any(), eq(Restaurant.class))).thenAnswer(invocation -> {
            InterestPointDTOForm dto = invocation.getArgument(0);
            Restaurant mappedPoint = new Restaurant();
            mappedPoint.setName(dto.getName());
            mappedPoint.setImageCoverUrl(dto.getImageCoverUrl());
            mappedPoint.setInterestPointType(InterestPointType.valueOf(dto.getInterestPointType()));
            return mappedPoint;
        });
        when(interestPointRepository.save(any())).thenReturn(new Restaurant());

        interestPointService.create(interestPointDTOForm);

        ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
        verify(interestPointRepository).save(captor.capture());
        Restaurant savedInterestPoint = captor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(interestPointDTOForm.getName(), savedInterestPoint.getName()),
                () -> Assertions.assertEquals(interestPointDTOForm.getInterestPointType(),
                        savedInterestPoint.getInterestPointType().toString())
        );
    }

    @Test
    public void create_shouldCreateOneExperience() {
        InterestPointDTOForm interestPointDTOForm = createInterestPoint("Lual nas Cataratas", "EXPERIENCE");
        when(interestPointRepository.getOnByName(any())).thenReturn(null);
        when(modelMapper.map(any(), eq(Experience.class))).thenAnswer(invocation -> {
            InterestPointDTOForm dto = invocation.getArgument(0);
            Experience mappedPoint = new Experience();
            mappedPoint.setName(dto.getName());
            mappedPoint.setImageCoverUrl(dto.getImageCoverUrl());
            mappedPoint.setInterestPointType(InterestPointType.valueOf(dto.getInterestPointType()));
            return mappedPoint;
        });
        when(interestPointRepository.save(any())).thenReturn(new Experience());

        interestPointService.create(interestPointDTOForm);

        ArgumentCaptor<Experience> captor = ArgumentCaptor.forClass(Experience.class);
        verify(interestPointRepository).save(captor.capture());
        Experience savedInterestPoint = captor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(interestPointDTOForm.getName(), savedInterestPoint.getName()),
                () -> Assertions.assertEquals(interestPointDTOForm.getInterestPointType(),
                        savedInterestPoint.getInterestPointType().toString())
        );
    }

    @Test
    public void create_shouldCreateOneEvent() {
        InterestPointDTOForm interestPointDTOForm = createInterestPoint("LatinoWare", "EVENT");
        when(interestPointRepository.getOnByName(any())).thenReturn(null);
        when(modelMapper.map(any(), eq(Event.class))).thenAnswer(invocation -> {
            InterestPointDTOForm dto = invocation.getArgument(0);
            Event mappedPoint = new Event();
            mappedPoint.setName(dto.getName());
            mappedPoint.setImageCoverUrl(dto.getImageCoverUrl());
            mappedPoint.setInterestPointType(InterestPointType.valueOf(dto.getInterestPointType()));
            return mappedPoint;
        });
        when(interestPointRepository.save(any())).thenReturn(new Event());

        interestPointService.create(interestPointDTOForm);

        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(interestPointRepository).save(captor.capture());
        Event savedInterestPoint = captor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(interestPointDTOForm.getName(), savedInterestPoint.getName()),
                () -> Assertions.assertEquals(interestPointDTOForm.getInterestPointType(),
                        savedInterestPoint.getInterestPointType().toString())
        );
    }

    @Test
    public void create_shouldCreateOneHotel() {
        InterestPointDTOForm interestPointDTOForm = createInterestPoint("Mabu Termas", "HOTEL");
        when(interestPointRepository.getOnByName(any())).thenReturn(null);
        when(modelMapper.map(any(), eq(Hotel.class))).thenAnswer(invocation -> {
            InterestPointDTOForm dto = invocation.getArgument(0);
            Hotel mappedPoint = new Hotel();
            mappedPoint.setName(dto.getName());
            mappedPoint.setImageCoverUrl(dto.getImageCoverUrl());
            mappedPoint.setInterestPointType(InterestPointType.valueOf(dto.getInterestPointType()));
            return mappedPoint;
        });
        when(interestPointRepository.save(any())).thenReturn(new Hotel());

        interestPointService.create(interestPointDTOForm);

        ArgumentCaptor<Hotel> captor = ArgumentCaptor.forClass(Hotel.class);
        verify(interestPointRepository).save(captor.capture());
        Hotel savedInterestPoint = captor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(interestPointDTOForm.getName(), savedInterestPoint.getName()),
                () -> Assertions.assertEquals(interestPointDTOForm.getInterestPointType(),
                        savedInterestPoint.getInterestPointType().toString())
        );
    }

    @Test
    public void create_shouldCreateOneTouristPoint() {
        InterestPointDTOForm interestPointDTOForm = createInterestPoint("Cataratas do Iguaçu", "TOURIST_POINT");
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


    public InterestPointDTOForm createInterestPoint(String name, String type){
        InterestPointDTOForm interestPointDTOForm = new InterestPointDTOForm();
        interestPointDTOForm.setName(name);
        interestPointDTOForm.setAverageValue(3);
        interestPointDTOForm.setShortDescription("\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        interestPointDTOForm.setImageCoverUrl(null);
        interestPointDTOForm.setLongDescription("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.");
        interestPointDTOForm.setDuration("2 horas");
        interestPointDTOForm.setInterestPointType(type);
        return interestPointDTOForm;
    }


}
