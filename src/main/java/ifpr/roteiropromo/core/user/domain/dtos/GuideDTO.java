    package ifpr.roteiropromo.core.user.domain.dtos;

import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GuideDTO {

    private String email;
    private String firstName;
    private String cadasturCode;

    private List<ItineraryDTO> itineraries;

}
