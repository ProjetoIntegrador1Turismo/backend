package ifpr.roteiropromo.core.review.domain.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTOForm {
    private String text;
    private String date;
    private Integer rating;
}
