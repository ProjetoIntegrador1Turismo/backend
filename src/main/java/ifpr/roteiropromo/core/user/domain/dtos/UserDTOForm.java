package ifpr.roteiropromo.core.user.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTOForm {

    private String firstName;
    private String lastName;
    private String email;
    private String cadasturCode;
    private String password;

    // Opcional para cadastro de admins através do admin panel ** COM SENHA **
    private boolean activeAdmin;

}
