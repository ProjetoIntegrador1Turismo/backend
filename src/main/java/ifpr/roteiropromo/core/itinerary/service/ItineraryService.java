package ifpr.roteiropromo.core.itinerary.service;

import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTOForm;
import ifpr.roteiropromo.core.itinerary.domain.entity.Itinerary;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final ModelMapper modelMapper;

    public Itinerary save(ItineraryDTOForm newItinerary){

        Itinerary itinerary = modelMapper.map(newItinerary, Itinerary.class);
        itinerary.setFreeEntrance(true);

        return itinerary;

    }


}
