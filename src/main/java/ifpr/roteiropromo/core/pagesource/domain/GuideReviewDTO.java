package ifpr.roteiropromo.core.pagesource.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuideReviewDTO {
    private String touristName;
    private String avatarUrl;
    private Long id;
    private String text;
    private String date;
    private Integer rating;
}
