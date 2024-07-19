package ifpr.roteiropromo.core.user.domain.dtos;

import ifpr.roteiropromo.core.guideprofile.domain.dtos.GuideProfileDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String cadasturCode;

    private Boolean isApproved;

    private boolean activeAdmin;
}
