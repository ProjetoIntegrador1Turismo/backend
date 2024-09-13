package ifpr.roteiropromo.core.itinerary.domain.dto;

import ifpr.roteiropromo.core.pagesource.domain.BasicGuideDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicItineraryWithGuideDTO {

    private Long id;
    private String title;
    private String imageCoverUrl;
    private BasicGuideDTO guide;
}
