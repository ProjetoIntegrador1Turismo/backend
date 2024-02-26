package ifpr.roteiropromo.core.interestPoint.domain.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Hotel extends InterestPoint implements Serializable {

    private Integer starsNumber;

    private Boolean isResort;

    private Boolean breakfastIncluded;



}
