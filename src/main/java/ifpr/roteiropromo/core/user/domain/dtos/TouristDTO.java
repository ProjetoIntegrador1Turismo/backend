package ifpr.roteiropromo.core.user.domain.dtos;


import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TouristDTO {

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private List<ItineraryDTO> interestedItineraries;

}
