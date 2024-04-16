package ifpr.roteiropromo.core.itinerary.domain.dto;

import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ItineraryResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Float mediumCost;
    private Integer days;
    private List<InterestPointDTO> interestPoints;
    private Long guideId;
    // getters and setters...
}