package ifpr.roteiropromo.core.itinerary.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Itinerary implements Serializable {

    private String name;
    private String type;
    private boolean freeEntrance;


}
