package ifpr.roteiropromo.core.itinerary.domain.dto;

import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTO;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.simple.BasicGenericDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private List<BasicGenericDTO> interestPoints;
}
