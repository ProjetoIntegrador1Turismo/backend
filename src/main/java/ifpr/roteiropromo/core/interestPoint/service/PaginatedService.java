package ifpr.roteiropromo.core.interestPoint.service;


import ifpr.roteiropromo.core.interestPoint.domain.dtos.simple.BasicGenericDTO;
import ifpr.roteiropromo.core.interestPoint.domain.entities.*;
import ifpr.roteiropromo.core.interestPoint.repository.*;
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

    public Page<BasicGenericDTO> findEventPaginated(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventPage = eventRepository.findAll(pageable);
        return eventPage.map(event -> new BasicGenericDTO(
                event.getId(),
                event.getImageCoverUrl(),
                event.getName(),
                event.getShortDescription()
        ));
    }

    public Page<BasicGenericDTO> findHotelPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);
        return hotelPage.map(hotel -> new BasicGenericDTO(
                hotel.getId(),
                hotel.getImageCoverUrl(),
                hotel.getName(),
                hotel.getShortDescription()
        ));
    }

    public Page<BasicGenericDTO> findExperiencesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Experience> experiencePage = experienceRepository.findAll(pageable);
        return experiencePage.map(exp -> new BasicGenericDTO(
                exp.getId(),
                exp.getImageCoverUrl(),
                exp.getName(),
                exp.getShortDescription()
        ));
    }

    public Page<BasicGenericDTO> findRestaurantsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Restaurant> restoPage = restaurantRepository.findAll(pageable);
        return restoPage.map(resto -> new BasicGenericDTO(
                resto.getId(),
                resto.getImageCoverUrl(),
                resto.getName(),
                resto.getShortDescription()
        ));
    }

    public Page<BasicGenericDTO> findTouristPointsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TouristPoint> touristPointPage = touristPointRepository.findAll(pageable);
        return touristPointPage.map(touristPoint -> new BasicGenericDTO(
                touristPoint.getId(),
                touristPoint.getImageCoverUrl(),
                touristPoint.getName(),
                touristPoint.getShortDescription()
        ));
    }
}
