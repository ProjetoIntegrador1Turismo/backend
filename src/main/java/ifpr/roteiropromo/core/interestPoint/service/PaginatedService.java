package ifpr.roteiropromo.core.interestPoint.service;


import ifpr.roteiropromo.core.interestPoint.domain.dtos.simple.BasicGenericDTO;
import ifpr.roteiropromo.core.interestPoint.domain.entities.Event;
import ifpr.roteiropromo.core.interestPoint.repository.EventRepository;
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

    public Page<BasicGenericDTO> findPaginated(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventPage = eventRepository.findAll(pageable);
        return eventPage.map(event -> new BasicGenericDTO(
                event.getId(),
                event.getImageCoverUrl(),
                event.getName(),
                event.getShortDescription()
        ));
    }


}
