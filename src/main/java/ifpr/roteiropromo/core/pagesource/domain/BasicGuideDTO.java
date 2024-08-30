package ifpr.roteiropromo.core.pagesource.domain;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BasicGuideDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String cadasturCode;
    private Double averageRating;
    private String profileImageUrl;
}
