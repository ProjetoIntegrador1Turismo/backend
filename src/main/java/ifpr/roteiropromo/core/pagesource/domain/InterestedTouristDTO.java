package ifpr.roteiropromo.core.pagesource.domain;

import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.user.domain.dtos.SimpleTouristDTO;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InterestedTouristDTO {

    private SimpleTouristDTO tourist;
    private BasicItineraryDTO itinerary;

}
