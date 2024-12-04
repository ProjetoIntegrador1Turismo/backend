package ifpr.roteiropromo.core.user.domain.dtos;


import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTO;
import ifpr.roteiropromo.core.itinerary.domain.dto.BasicItineraryWithGuideDTO;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TouristDTO {

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;

    private List<BasicItineraryWithGuideDTO> interestedItineraries;
    private List<CommentDTO> comments;
    private List<ReviewDTO> reviews;

}