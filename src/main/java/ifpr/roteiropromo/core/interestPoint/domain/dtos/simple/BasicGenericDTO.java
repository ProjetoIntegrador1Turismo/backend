package ifpr.roteiropromo.core.interestPoint.domain.dtos.simple;

import ifpr.roteiropromo.core.enums.InterestPointType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicGenericDTO {

    private Long id;
    private String name;
    private String shortDescription;
    private String imageCoverUrl;
    private InterestPointType interestPointType;

}
