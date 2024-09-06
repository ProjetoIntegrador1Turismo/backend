package ifpr.roteiropromo.core.comments.domain.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTOForm {

    @Schema(description = "Tourist's comment text", example = "This place is amazing!")
    private String text;
    @Schema(description = "Visit date")
    private String wasVisitingDate;
    @Schema(description = "Rating of the visit, between 1 and 5", example = "5")
    private Integer rating;

}
