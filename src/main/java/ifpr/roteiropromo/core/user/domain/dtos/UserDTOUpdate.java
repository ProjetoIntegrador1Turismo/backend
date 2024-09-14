package ifpr.roteiropromo.core.user.domain.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTOUpdate {

    private Long id;
//    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String newPassword;
    private String phone;

}
