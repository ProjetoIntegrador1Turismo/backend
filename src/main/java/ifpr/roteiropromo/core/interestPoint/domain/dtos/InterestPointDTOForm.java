package ifpr.roteiropromo.core.interestPoint.domain.dtos;

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

    // CAMPOS ADICIONADOS EM 23/07 PRA TESTE REMOVER DEPOIS
    String duration;
    String averageValue;
    String imageCoverUrl;

}