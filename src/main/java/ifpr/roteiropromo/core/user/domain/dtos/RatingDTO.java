package ifpr.roteiropromo.core.user.domain.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RatingDTO {
    private String guideEmail;
    private String userEmail;
    private String text;
    private String date;
    private Integer rating;
}

