package ifpr.roteiropromo.core.interestPoint.domain.dtos;

import ifpr.roteiropromo.core.address.model.dtos.AddressDTO;
import ifpr.roteiropromo.core.address.model.entities.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InterestPointDTO {

    private String name;
    private AddressDTO address;
    private Integer averageValue;
    private String shortDescription;
    //Não permitir a att do tipo de ponto de interesse, pois são tabelas distintas!
    //private String interestPointType;
    private String longDescription;
    private Integer starsNumber;
    private Boolean isResort;
    private Boolean breakfastIncluded;
    private String foodType;

}