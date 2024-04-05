package ifpr.roteiropromo.core.interestPoint.domain.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Restaurant extends InterestPoint{

    private String foodType;


}