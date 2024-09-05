package ifpr.roteiropromo.core.itinerary.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItineraryUpdateDTO {

    private String title;
    private String description;
    private Float mediumCost;
    private Integer days;
    private List<Long> interestPointsId;

}
