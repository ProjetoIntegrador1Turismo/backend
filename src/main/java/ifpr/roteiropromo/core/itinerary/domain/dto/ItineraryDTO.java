package ifpr.roteiropromo.core.itinerary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItineraryDTO {

    private Long id;
    private String name;
    private String type;
    private boolean freeEntrance;

}
