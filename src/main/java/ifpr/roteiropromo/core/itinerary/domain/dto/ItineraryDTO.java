package ifpr.roteiropromo.core.itinerary.domain.dto;

import ifpr.roteiropromo.core.guideprofile.domain.dtos.GuideProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItineraryDTO {
    private Long id;
    private String title;
    private String description;
    private Float mediumCost;
    private Integer days;
//    private GuideProfileDTO guideProfile;

}
