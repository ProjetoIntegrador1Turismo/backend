package ifpr.roteiropromo.core.itinerary.domain.dto;

import ifpr.roteiropromo.core.guideprofile.domain.entities.GuideProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItineraryDTOForm {

    private String title;
    private String description;
    private Float mediumCost;
    private Integer days;

    //tests 12/04/2024
    private Long guideProfileId ;
}
