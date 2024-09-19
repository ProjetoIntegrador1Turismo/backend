package ifpr.roteiropromo.core.pagesource.service;

import ifpr.roteiropromo.core.admin.domain.FeaturedTouristPoint;
import ifpr.roteiropromo.core.admin.service.AdminService;
import ifpr.roteiropromo.core.enums.EntityTypeName;
import ifpr.roteiropromo.core.interestPoint.domain.entities.*;
import ifpr.roteiropromo.core.interestPoint.repository.*;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.itinerary.repository.ItineraryRepository;
import ifpr.roteiropromo.core.pagesource.domain.BasicPointDTO;
import ifpr.roteiropromo.core.pagesource.domain.HomePageDTO;
import ifpr.roteiropromo.core.pagesource.domain.InterestPointCardDTO;
import ifpr.roteiropromo.core.pagesource.domain.TopGuideDTO;
import ifpr.roteiropromo.core.user.service.GuideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomePageSourceService {

    private final InterestPointService interestPointService;
    private final TouristPointRepository touristPointRepository;
    private final ExperienceRepository experienceRepository;
    private final ItineraryRepository itineraryRepository;
    private final RestaurantRepository restaurantRepository;
    private final HotelRepository hotelRepository;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final AdminService adminService;
    private final GuideService guideService;

    public HomePageDTO getHomePageData(){
        HomePageDTO homePageDTO = new HomePageDTO();
        homePageDTO.setTop3InterestPoints(getTop3InterestPoints());
        homePageDTO.setFirstSlider(getRandomPointsToFirstSlider());
        homePageDTO.setSecondSlider(getRandomPointsToSecondSlider());
        homePageDTO.setTopGuides(getTop5Guides());
        return homePageDTO;
    }



    private List<BasicPointDTO> getRandomPointsToSecondSlider() {
        //        second slider vir roteiros, hoteis e expêriencias.
        List<BasicPointDTO> basicPointDTO = new ArrayList<>();
//        basicPointDTO.addAll(getRandomRestaurant());
//        basicPointDTO.addAll(getRandomEvents());
        basicPointDTO.addAll(getRandomHotels()); //hoteis
        basicPointDTO.addAll(getRandomExperiences()); //expêriencias
        basicPointDTO.addAll(getRandomItineraries()); //roteiros
        return basicPointDTO;
    }

    private List<BasicPointDTO> getRandomEvents() {
        List<Event> events = eventRepository.findAll();
        Collections.shuffle(events);
        List<Event> random3events = new ArrayList<>();
        for (int i = 0; i < Math.min(3, events.size()); i++) {
            random3events.add(events.get(i));
        }
        return mapList(random3events, BasicPointDTO.class);
    }

    private List<BasicPointDTO> getRandomHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        Collections.shuffle(hotels);
        List<Hotel> random3hotels = new ArrayList<>();
        for (int i = 0; i < Math.min(3, hotels.size()); i++) {
            random3hotels.add(hotels.get(i));
        }
        return mapList(random3hotels, BasicPointDTO.class);
    }

    private List<BasicPointDTO> getRandomRestaurant() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        Collections.shuffle(restaurants);
        List<Restaurant> random3restaurants = new ArrayList<>();
        for (int i = 0; i < Math.min(3, restaurants.size()); i++) {
            random3restaurants.add(restaurants.get(i));
        }
        return mapList(random3restaurants, BasicPointDTO.class);
    }

    private List<BasicPointDTO> getRandomPointsToFirstSlider() {
        //first slider vir apenas restaurantes, eventos e pontos turisticos
        List<BasicPointDTO> basicPointDTO = new ArrayList<>();
        basicPointDTO.addAll(getRandomTouristPoints()); //pontos turisticos
        basicPointDTO.addAll(getRandomEvents()); // eventos
        basicPointDTO.addAll(getRandomRestaurant()); //restaurantes
//        basicPointDTO.addAll(getRandomExperiences());
//        basicPointDTO.addAll(getRandomItineraries());
        return basicPointDTO;
    }

    private List<BasicPointDTO> getRandomExperiences() {
        List<Experience> experiences = experienceRepository.findAll();
        Collections.shuffle(experiences);
        List<Experience> random3Experience = new ArrayList<>();
        for (int i = 0; i < Math.min(3, experiences.size()); i++) {
            random3Experience.add(experiences.get(i));
        }
        return mapList(random3Experience, BasicPointDTO.class);
    }

    private List<BasicPointDTO> getRandomTouristPoints() {
        List<TouristPoint> touristPoints = touristPointRepository.findAll();
        Collections.shuffle(touristPoints);
        List<TouristPoint> random3TouristPoints = new ArrayList<>();
        for (int i = 0; i < Math.min(3, touristPoints.size()); i++) {
            random3TouristPoints.add(touristPoints.get(i));
        }
        return mapList(random3TouristPoints, BasicPointDTO.class);
    }

    private List<BasicPointDTO> getRandomItineraries() {
        List<Itinerary> itineraries = itineraryRepository.findAll();
        Collections.shuffle(itineraries);
        List<Itinerary> random3itineraries = new ArrayList<>();
        for (int i = 0; i < Math.min(3, itineraries.size()); i++) {
            random3itineraries.add(itineraries.get(i));
        }

        List<BasicPointDTO> basicPointDTO = new ArrayList<>();
        random3itineraries.forEach(itinerary -> {
            BasicPointDTO pointDTO = modelMapper.map(itinerary, BasicPointDTO.class);
            pointDTO.setName(itinerary.getTitle());
            pointDTO.setInterestPointType(EntityTypeName.ITINERARY.toString());
            basicPointDTO.add(pointDTO);
        });
        return basicPointDTO;
    }


    private List<InterestPointCardDTO> getTop3InterestPoints() {
        List<FeaturedTouristPoint> principalPoints = adminService.getAllFeaturedPoints();
        List<InterestPointCardDTO> cardDTOS = new ArrayList<>();
        principalPoints.forEach(principalPoint -> {
            cardDTOS.add(modelMapper.map(principalPoint.getInterestPoint(), InterestPointCardDTO.class));
        });
        return cardDTOS;
    }


    private List<TopGuideDTO> getTop5Guides() {
        return guideService.getTopGuidesDTO();
    }



    public <S, D> List<D> mapList(List<S> source, Class<D> destinationType) {
        return source.stream()
                .map(element -> modelMapper.map(element, destinationType))
                .collect(Collectors.toList());
    }


}
