package ifpr.roteiropromo.core.itinerary.service;

import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
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

    public Itinerary create(ItineraryDTOForm itineraryDTOForm){
        Itinerary itinerary = modelMapper.map(itineraryDTOForm, Itinerary.class);
        return itineraryRepository.save(itinerary);
    }

    public List<Itinerary> findAll() {
        return itineraryRepository.findAll();
    }

    public Itinerary update(ItineraryDTO itinerary, Long id){
        Itinerary itineraryFound = itineraryRepository.findById(id).orElseThrow();
        itineraryFound.setTitle(itinerary.getTitle());
        itineraryFound.setDescription(itinerary.getDescription());
        itineraryFound.setMediumCost(itinerary.getMediumCost());
        itineraryFound.setDays(itinerary.getDays());
        return itineraryRepository.save(itineraryFound);
    }
}
