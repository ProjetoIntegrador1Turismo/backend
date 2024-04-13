package ifpr.roteiropromo.core.guideprofile.domain.dtos;

import ifpr.roteiropromo.core.user.domain.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GuideProfileDTOForm {

    private UserDTO user;
}