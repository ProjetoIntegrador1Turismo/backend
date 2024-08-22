package ifpr.roteiropromo.core.interestPoint.service;


import ifpr.roteiropromo.core.interestPoint.domain.dtos.simple.BasicGenericDTO;
import ifpr.roteiropromo.core.interestPoint.domain.entities.*;
import ifpr.roteiropromo.core.interestPoint.repository.*;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.itinerary.repository.ItineraryRepository;
import ifpr.roteiropromo.core.pagesource.domain.BasicItineraryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaginatedService {

    private final EventRepository eventRepository;
    private final HotelRepository hotelRepository;
    private final ExperienceRepository experienceRepository;
    private final RestaurantRepository restaurantRepository;
    private final TouristPointRepository touristPointRepository;
    private final InterestPointRepository interestPointRepository;
    private final ItineraryRepository itineraryRepository;

    public Page<BasicGenericDTO> findEventPaginated(int page, int size, String name){
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventPage;
        if (name.isEmpty()){
            eventPage = eventRepository.findAll(pageable);
        } else {
            eventPage = eventRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return eventPage.map(event -> new BasicGenericDTO(
                event.getId(),
                event.getName(),
                event.getShortDescription(),
                event.getImageCoverUrl(),
                event.getInterestPointType()
        ));
    }

    public Page<BasicGenericDTO> findHotelPaginated(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hotel> hotelPage;
        if (name.isEmpty()){
            hotelPage = hotelRepository.findAll(pageable);
        } else {
            hotelPage = hotelRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return hotelPage.map(hotel -> new BasicGenericDTO(
                hotel.getId(),
                hotel.getName(),
                hotel.getShortDescription(),
                hotel.getImageCoverUrl(),
                hotel.getInterestPointType()
        ));
    }

    public Page<BasicGenericDTO> findExperiencesPaginated(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Experience> experiencePage;
        if (name.isEmpty()){
            experiencePage = experienceRepository.findAll(pageable);
        } else {
            experiencePage = experienceRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return experiencePage.map(exp -> new BasicGenericDTO(
                exp.getId(),
                exp.getName(),
                exp.getShortDescription(),
                exp.getImageCoverUrl(),
                exp.getInterestPointType()
        ));
    }

    public Page<BasicGenericDTO> findRestaurantsPaginated(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Restaurant> restoPage;
        if (name.isEmpty()){
            restoPage = restaurantRepository.findAll(pageable);
        }else {
            restoPage = restaurantRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return restoPage.map(resto -> new BasicGenericDTO(
                resto.getId(),
                resto.getName(),
                resto.getShortDescription(),
                resto.getImageCoverUrl(),
                resto.getInterestPointType()
        ));
    }

    public Page<BasicGenericDTO> findTouristPointsPaginated(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TouristPoint> touristPointPage;
        if (name.isEmpty()){
            touristPointPage = touristPointRepository.findAll(pageable);
        }else {
            touristPointPage = touristPointRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return touristPointPage.map(touristPoint -> new BasicGenericDTO(
                touristPoint.getId(),
                touristPoint.getName(),
                touristPoint.getShortDescription(),
                touristPoint.getImageCoverUrl(),
                touristPoint.getInterestPointType()
        ));
    }

    public Page<BasicGenericDTO> findAllPaginated(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page,size);
        Page<InterestPoint> interestPointsPage;
        if(name.isEmpty()){
            interestPointsPage = interestPointRepository.findAll(pageable);
        }else {
            interestPointsPage = interestPointRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return interestPointsPage.map(interestPoint -> new BasicGenericDTO(
                interestPoint.getId(),
                interestPoint.getName(),
                interestPoint.getShortDescription(),
                interestPoint.getImageCoverUrl(),
                interestPoint.getInterestPointType()
        ));
    }

    public Page<BasicItineraryDTO> findItinerariesPaginated(int page, int size, String query) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Itinerary> itineraryPage;
        if (query.isEmpty()){
            itineraryPage = itineraryRepository.findAll(pageable);
        }else {
            itineraryPage = itineraryRepository.findItinerariesByTitleContainingIgnoreCase(query, pageable);
        }
        return itineraryPage.map(itinerary -> new BasicItineraryDTO(
                itinerary.getId(),
                itinerary.getTitle(),
                itinerary.getImageCoverUrl()
        ));
    }
}
