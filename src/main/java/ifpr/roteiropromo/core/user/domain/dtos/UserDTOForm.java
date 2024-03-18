package ifpr.roteiropromo.core.user.domain.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTOForm {


    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String cadasturCode;

    private String password;

}
