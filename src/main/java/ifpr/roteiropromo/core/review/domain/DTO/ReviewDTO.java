package ifpr.roteiropromo.core.review.domain.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class ReviewDTO {
    private Long id;
    private String text;
    private String date;
    private Integer rating;
    private Long guideId;
    private Long userId;
}
