package ifpr.roteiropromo.core.user.domain.dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleTouristDTO {

    private Long id;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String profileImageUrl;

}
