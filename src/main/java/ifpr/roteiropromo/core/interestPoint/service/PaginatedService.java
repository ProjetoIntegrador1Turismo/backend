package ifpr.roteiropromo.core.interestPoint.service;


import ifpr.roteiropromo.core.interestPoint.domain.dtos.simple.BasicGenericDTO;
import ifpr.roteiropromo.core.interestPoint.domain.entities.Event;
import ifpr.roteiropromo.core.interestPoint.domain.entities.Experience;
import ifpr.roteiropromo.core.interestPoint.domain.entities.Hotel;
import ifpr.roteiropromo.core.interestPoint.repository.EventRepository;
import ifpr.roteiropromo.core.interestPoint.repository.ExperienceRepository;
import ifpr.roteiropromo.core.interestPoint.repository.HotelRepository;
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
}
