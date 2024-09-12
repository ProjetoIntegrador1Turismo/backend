package ifpr.roteiropromo.core.comments.domain.DTO;


import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.pagesource.domain.BasicPointDTO;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private Long id;
    private String text;
    private String wasVisitingDate;
    private Integer rating;
//    private InterestPoint interestPoint;
    private BasicPointDTO interestPoint;
    private String touristName;

}
