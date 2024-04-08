package ifpr.roteiropromo.core.comments.domain.DTO;

import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTOForm {

    private String text;
    private String wasVisitingDate;
    private Integer rating;

}
