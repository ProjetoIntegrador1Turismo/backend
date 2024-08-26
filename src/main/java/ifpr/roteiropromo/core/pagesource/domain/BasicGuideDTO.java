package ifpr.roteiropromo.core.pagesource.domain;

import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasicGuideDTO {
    private Long id;
    private String firstName;
    private String cadasturCode;
    private Double averageRating;
}
