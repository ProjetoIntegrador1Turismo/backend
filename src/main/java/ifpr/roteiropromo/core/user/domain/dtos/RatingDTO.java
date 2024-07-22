package ifpr.roteiropromo.core.user.domain.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {
    private String guideEmail;
    private String userEmail;
    private int rating;
}
