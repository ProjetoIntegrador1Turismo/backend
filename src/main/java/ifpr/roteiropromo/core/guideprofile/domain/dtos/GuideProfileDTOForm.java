package ifpr.roteiropromo.core.guideprofile.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GuideProfileDTOForm {

    private String name;
    private String email;
    private String contact;
    private String description;
    private String cadastur;

}
