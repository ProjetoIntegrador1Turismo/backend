package ifpr.roteiropromo.core.interestPoint.domain.dtos;

import ifpr.roteiropromo.core.addres.model.entities.Address;
import ifpr.roteiropromo.core.enums.InterestPointType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private Long id;
    private String name;
    private Address address;
    private Integer averageValue;
    private String shortDescription;
    private InterestPointType interestPointType;
    private String longDescription;
    private String foodType;

}
