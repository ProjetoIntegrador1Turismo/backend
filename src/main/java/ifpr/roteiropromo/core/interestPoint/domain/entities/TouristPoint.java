package ifpr.roteiropromo.core.interestPoint.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TouristPoint extends InterestPoint{

    @Column(length = 500)
    private String longDescription;

}