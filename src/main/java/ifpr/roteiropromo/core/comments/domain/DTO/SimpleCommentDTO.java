package ifpr.roteiropromo.core.comments.domain.DTO;

import ifpr.roteiropromo.core.user.domain.dtos.BasicTouristDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleCommentDTO {

    private Long id;
    private String text;
    private String wasVisitingDate;
    private Integer rating;

    private BasicTouristDTO tourist;
}
