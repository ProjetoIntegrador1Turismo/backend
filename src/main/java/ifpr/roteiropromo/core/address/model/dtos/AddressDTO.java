package ifpr.roteiropromo.core.address.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private String road;
    private String number;
    private String zipCode;
}
