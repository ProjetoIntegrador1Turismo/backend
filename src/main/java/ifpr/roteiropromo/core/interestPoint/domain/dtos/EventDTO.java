package ifpr.roteiropromo.core.interestPoint.domain.dtos;

import ifpr.roteiropromo.core.addres.model.entities.Addres;
import ifpr.roteiropromo.core.enums.InterestPointType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private Long id;
    private String name;
    private Addres addres;
    private Integer averageValue;
    private String shortDescription;
    private InterestPointType interestPointType;
    private String longDescription;
    private String foodType;

}
