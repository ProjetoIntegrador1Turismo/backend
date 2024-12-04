package ifpr.roteiropromo.core.pagesource.domain;

import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTO;
import ifpr.roteiropromo.core.comments.domain.DTO.SimpleCommentDTO;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TourPageDTO {

    InterestPointDTO interestPoint;
    List<TopGuideDTO> guidesWhoOfferThisTour;
    List<SimpleCommentDTO> comments;

}
