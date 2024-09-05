package ifpr.roteiropromo.core.interestPoint.domain.dtos;

import ifpr.roteiropromo.core.address.model.dtos.AddressDTO;
import ifpr.roteiropromo.core.address.model.entities.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InterestPointDTOForm {

    @Schema(description = "Interest Point name", example = "Temple of the Sun")
    String name;
    @Schema(description = "Short descriptive text", example = "A beautiful temple in the middle of the city.", maximum = "255")
    String shortDescription;
    @Schema(description = "One of the available types", example = "TOURIST_POINT", allowableValues = {"TOURIST_POINT", "RESTAURANT", "HOTEL", "EXPERIENCE", "EVENT"})
    String interestPointType;
    @Schema(description = "Image chosen for cover")
    String imageCoverUrl;
    @Schema(description = "The time it takes to visit", example = "2 hours")
    String duration;
    @Schema(description = "Average value, between 1 and 5", example = "5")
    Integer averageValue;
    AddressDTO address;
    @Schema(description = "Long descriptive text", example = "The Temple of the Sun is a beautiful place to visit, with a lot of history.", maximum = "500")
    String longDescription;
    @Schema(description = "Minimum age required to visit", example = "18 years plus")
    String requiredAge;
    String date;
    @Schema(description = "Stars number, between 1 and 5", example = "4")
    Integer starsNumber;
    @Schema(description = "If the Hotel is a resort", example = "true")
    Boolean isResort;
    @Schema(description = "If the hotel includes breakfast", example = "true")
    Boolean breakfastIncluded;
    @Schema(description = "The type of food served", example = "Italian")
    String foodType;
}


