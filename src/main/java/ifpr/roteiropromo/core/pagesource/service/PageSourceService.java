package ifpr.roteiropromo.core.pagesource.service;

import ifpr.roteiropromo.core.interestPoint.domain.entities.*;
import ifpr.roteiropromo.core.interestPoint.repository.*;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.itinerary.repository.ItineraryRepository;
import ifpr.roteiropromo.core.pagesource.domain.BasicPointDTO;
import ifpr.roteiropromo.core.pagesource.domain.HomePageDTO;
import ifpr.roteiropromo.core.pagesource.domain.InterestPointCardDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageSourceService {

    private final InterestPointService interestPointService;
    private final TouristPointRepository touristPointRepository;
    private final ExperienceRepository experienceRepository;
    private final ItineraryRepository itineraryRepository;
    private final RestaurantRepository restaurantRepository;
    private final HotelRepository hotelRepository;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    public HomePageDTO getHomePageData(){
        HomePageDTO homePageDTO = new HomePageDTO();
        homePageDTO.setTop3InterestPoints(getTop3InterestPoints());
        homePageDTO.setFirstSlider(getRandomPointsToFirstSlider()); ////pontos, roteiros ou experiencias
        homePageDTO.setSecondSlider(getRandomPointsToSecondSlider()); //restaurantes, hoteis e eventos

        return homePageDTO;
    }

    private List<BasicPointDTO> getRandomPointsToSecondSlider() {
        List<BasicPointDTO> basicPointDTO = new ArrayList<>();
        basicPointDTO.addAll(getRandomRestaurant());
        basicPointDTO.addAll(getRandomHotels());
        basicPointDTO.addAll(getRandomEvents());
        return basicPointDTO;
    }

    private List<BasicPointDTO> getRandomEvents() {
        List<Event> events = eventRepository.findAll();
        Collections.shuffle(events);
        List<Event> random3Events = events.subList(0, 3);
        return mapList(random3Events, BasicPointDTO.class);
    }

    private List<BasicPointDTO> getRandomHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        Collections.shuffle(hotels);
        List<Hotel> random3Hotels = hotels.subList(0, 3);
        return mapList(random3Hotels, BasicPointDTO.class);
    }

    private List<BasicPointDTO> getRandomRestaurant() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        Collections.shuffle(restaurants);
        List<Restaurant> random3Restaurants = restaurants.subList(0, 3);
        return mapList(random3Restaurants, BasicPointDTO.class);
    }

    private List<BasicPointDTO> getRandomPointsToFirstSlider() {
        List<BasicPointDTO> basicPointDTO = new ArrayList<>();
        basicPointDTO.addAll(getRandomTouristPoints());
        basicPointDTO.addAll(getRandomExperiences());
        basicPointDTO.addAll(getRandomItineraries());
        return basicPointDTO;
    }

    private List<BasicPointDTO> getRandomExperiences() {
        List<Experience> experiences = experienceRepository.findAll();
        Collections.shuffle(experiences);
        List<Experience> random3Experiences = experiences.subList(0, 3);
        return mapList(random3Experiences, BasicPointDTO.class);
    }

    private List<BasicPointDTO> getRandomTouristPoints() {
        List<TouristPoint> touristPoints = touristPointRepository.findAll();
        Collections.shuffle(touristPoints);
        List<TouristPoint> random3TouristPoints = touristPoints.subList(0, 3);
        return mapList(random3TouristPoints, BasicPointDTO.class);
    }

    private List<BasicPointDTO> getRandomItineraries() {
        List<Itinerary> itineraries = itineraryRepository.findAll();
        Collections.shuffle(itineraries);
        List<Itinerary> random3Itineraries = itineraries.subList(0, 3);
        List<BasicPointDTO> basicPointDTO = new ArrayList<>();
        for (Itinerary itinerary : random3Itineraries) {
            BasicPointDTO basicPoint = new BasicPointDTO();
            basicPoint.setId(itinerary.getId());
            basicPoint.setName(itinerary.getTitle());
            basicPoint.setImageCoverUrl("http://localhost:8081/uploads/roteiro.jpeg");
            basicPointDTO.add(basicPoint);
        }
        return basicPointDTO;
    }


    //AJUSTAR PARA CAPTURAR OS 3 PONTOS DO ARQUIVO DE CONFIGURAÇÃO
    private List<InterestPointCardDTO> getTop3InterestPoints() {
        List<InterestPointCardDTO> top3InterestPoints = new ArrayList<>();
        InterestPoint cataratas = interestPointService.getOne(3L);
        InterestPoint itapu = interestPointService.getOne(4L);
        InterestPoint parque = interestPointService.getOne(2L);
        top3InterestPoints.add(modelMapper.map(cataratas, InterestPointCardDTO.class));
        top3InterestPoints.add(modelMapper.map(itapu, InterestPointCardDTO.class));
        top3InterestPoints.add(modelMapper.map(parque, InterestPointCardDTO.class));
        return top3InterestPoints;
    }

    public <S, D> List<D> mapList(List<S> source, Class<D> destinationType) {
        return source.stream()
                .map(element -> modelMapper.map(element, destinationType))
                .collect(Collectors.toList());
    }


}
