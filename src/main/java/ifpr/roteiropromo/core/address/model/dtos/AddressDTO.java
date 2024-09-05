package ifpr.roteiropromo.core.address.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    @Schema(description = "Information about street/avenue and city name", example = "Temple of the Sun Street - Iguaçu Falls")
    private String road;
    @Schema(description = "Number location", example = "1234")
    private String number;
    @Schema(description = "The postal zip code number", example = "85853-000")
    private String zipCode;
}
