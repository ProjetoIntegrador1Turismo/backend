package ifpr.roteiropromo.core.itinerary.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItineraryUpdateDTO {

    private String title;
    private String description;
    private Float mediumCost;
    private Integer days;

}
