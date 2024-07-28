    package ifpr.roteiropromo.core.user.domain.dtos;

import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GuideDTO {
    private Long id;
    private String firstName;
    private String cadasturCode;
    private Boolean isApproved;
    private List<ItineraryDTO> itineraries;
    private List<ReviewDTO> reviews;
}
