package ifpr.roteiropromo.core.user.domain.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleGuideDTO {

    private Long id;
    private String firstName;
    private String userName;
    private String cadasturCode;
    private Boolean isApproved;
    private String lastName;
    private String profileImageUrl;
    private String email;

}
