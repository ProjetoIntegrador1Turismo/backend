package ifpr.roteiropromo.core.interestPoint.domain.dtos;

import ifpr.roteiropromo.core.addres.domain.entities.Addres;
import ifpr.roteiropromo.core.enums.InterestPointType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {

    private Long id;
    private String name;
    private Addres addres;
    private Integer averageValue;
    private String shortDescription;
    private InterestPointType interestPointType;
    private Integer starsNumber;
    private Boolean resort;
    private Boolean breakfastIncluded;

}
