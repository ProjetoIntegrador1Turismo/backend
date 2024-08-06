package ifpr.roteiropromo.core.interestPoint.domain.dtos;

import ifpr.roteiropromo.core.address.model.dtos.AddressDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InterestPointUpdateDTO {
    private String name;
    private String road;
    private String number;
    private String zipCode;
    private Integer averageValue;
    private String shortDescription;
    private String longDescription;
    private Integer starsNumber;
    private Boolean isResort;
    private Boolean breakfastIncluded;
    private String foodType;
    private String date;
    private String duration;
    private String requiredAge;
}
