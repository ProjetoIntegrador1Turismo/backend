package ifpr.roteiropromo.core.pagesource.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopGuideDTO {
    private Long id;
    private String firstName;
    private double averageRating;
    private String profileImageUrl;

}
