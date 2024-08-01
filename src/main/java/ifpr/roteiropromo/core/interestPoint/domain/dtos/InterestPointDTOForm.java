package ifpr.roteiropromo.core.interestPoint.domain.dtos;

import ifpr.roteiropromo.core.address.model.dtos.AddressDTO;
import ifpr.roteiropromo.core.address.model.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InterestPointDTOForm {
    String name;
    String shortDescription;
    String interestPointType;
    String imageCoverUrl;
    String duration;
    String averageValue;
    AddressDTO address;
    String longDescription;
    String requiredAge;
    String date;
    Integer starsNumber;
    Boolean isResort;
    Boolean breakfastIncluded;
    String foodType;
}


