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

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    //Atributo opcional p/ cadastros de guia.
    private String cadasturCode;

    private String password;

}
