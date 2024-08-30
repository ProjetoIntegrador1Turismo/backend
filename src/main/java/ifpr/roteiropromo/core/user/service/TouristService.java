package ifpr.roteiropromo.core.user.service;

import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.itinerary.service.ItineraryService;
import ifpr.roteiropromo.core.user.domain.dtos.TouristDTO;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TouristService {


    private final JwtTokenHandler jwtTokenHandler;
    private final ItineraryService itineraryService;
    private final UserService userService;
    private final ModelMapper mapper;

    public TouristDTO signalItineraryInterest(Long itineraryId) {
        AuthenticatedUserDTO userAthenticated = jwtTokenHandler.getUserDataFromToken();
        Tourist touristFound = userService.getTouristByEmail(userAthenticated.getEmail());
        Itinerary itinerary = itineraryService.findOneById(itineraryId);
        touristFound.getInterestedItineraries().add(itinerary);
        userService.updateTourist(touristFound);
        return mapper.map(userService.getTouristByEmail(userAthenticated.getEmail()), TouristDTO.class);
    }


    public void removeInterestedItinerary(Long itineraryId) {
        AuthenticatedUserDTO userAthenticated = jwtTokenHandler.getUserDataFromToken();
        Tourist touristFound = userService.getTouristByEmail(userAthenticated.getEmail());
        if(!removeInterestItineraryOrFail(touristFound.getInterestedItineraries(), itineraryId)){
            throw new ServiceError("Tourist authenticated dont have signed interest in a itinerary whit id: " + itineraryId);
        }else {
            userService.updateTourist(touristFound);
        }
    }

    private boolean removeInterestItineraryOrFail(List<Itinerary> interestedItineraries, Long itineraryId) {
        return interestedItineraries.removeIf(itinerary -> itinerary.getId().equals(itineraryId));
    }
}
