package ifpr.roteiropromo.core.review.domain.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {

    private Long id;
    private String text;
    private String date;
    private Integer rating;
    private String touristName;
}
