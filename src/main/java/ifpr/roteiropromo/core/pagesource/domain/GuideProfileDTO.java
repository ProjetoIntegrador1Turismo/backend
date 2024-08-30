package ifpr.roteiropromo.core.pagesource.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GuideProfileDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String cadasturCode;
    private Double averageRating;
    private String profileImageUrl;
    private List<GuideReviewDTO> reviews;
    private List<BasicItineraryDTO> itineraries;

}
