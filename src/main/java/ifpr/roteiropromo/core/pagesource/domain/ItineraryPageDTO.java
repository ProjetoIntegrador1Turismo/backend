package ifpr.roteiropromo.core.pagesource.domain;

import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ItineraryPageDTO {

    private BasicGuideDTO guide;
    private ItineraryDTO itinerary;
    private List<GuideReviewDTO> reviews;

}
