package ifpr.roteiropromo.core.itinerary.service;

import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTOForm;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.itinerary.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final ModelMapper modelMapper;
    private final ItineraryRepository itineraryRepository;

    public Itinerary save(ItineraryDTOForm newItinerary){

        Itinerary itinerary = modelMapper.map(newItinerary, Itinerary.class);
        itinerary.setFreeEntrance(true);

        return itineraryRepository.save(itinerary);

    }


    public List<Itinerary> findAll() {
        return itineraryRepository.findAll();
    }
}
