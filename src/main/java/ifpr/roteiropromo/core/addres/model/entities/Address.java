package ifpr.roteiropromo.core.addres.model.entities;

import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String road;
    private Integer number;
    private Integer zipCode;

}
