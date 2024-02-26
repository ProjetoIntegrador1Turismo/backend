package ifpr.roteiropromo.core.interestPoint.domain.dtos;

import ifpr.roteiropromo.core.addres.model.entities.Addres;
import ifpr.roteiropromo.core.enums.InterestPointType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InterestPointDTOForm {

    String name;
    Addres addres;
    Integer averageValue;
    String description;
    InterestPointType interestPointType;


}
