package ifpr.roteiropromo.core.interestPoint.domain.dtos;

import ifpr.roteiropromo.core.address.model.entities.Address;
import ifpr.roteiropromo.core.enums.InterestPointType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TouristPointDTO {

    private Long id;
    private String name;
    private Address address;
    private Integer averageValue;
    private String shortDescription;
    private InterestPointType interestPointType;
    private String longDescription;


}
