package ifpr.roteiropromo.core.guideprofile.domain.dtos;

import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GuideProfileDTO {

    private Long id;
    private UserDTO user;
    private List<ItineraryDTO> itineraries;
}