package ifpr.roteiropromo.core.interestPoint.domain.dtos;

import ifpr.roteiropromo.core.addres.model.entities.Addres;
import ifpr.roteiropromo.core.enums.InterestPointType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDTO {

    private Long id;
    private String name;
    private Addres addres;
    private Integer averageValue;
    private String shortDescription;
    private InterestPointType interestPointType;
    private String longDescription;

}