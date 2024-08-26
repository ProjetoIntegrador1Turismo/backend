package ifpr.roteiropromo.core.pagesource.domain;

import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItineraryPageDTO {

    private BasicGuideDTO guide;
    private ItineraryDTO itinerary;
    private List<ReviewDTO> reviews;

}
