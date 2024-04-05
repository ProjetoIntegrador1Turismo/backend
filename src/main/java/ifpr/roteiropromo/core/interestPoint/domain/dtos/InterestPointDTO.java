package ifpr.roteiropromo.core.interestPoint.domain.dtos;

import ifpr.roteiropromo.core.address.model.entities.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InterestPointDTO {

    private String name;
    private Address address;
    private Integer averageValue;
    private String shortDescription;
    private String interestPointType;
    private String longDescription;
    private Integer starsNumber;
    private Boolean isResort;
    private Boolean breakfastIncluded;
    private String foodType;

}