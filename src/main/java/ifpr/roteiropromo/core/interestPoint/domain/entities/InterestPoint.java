package ifpr.roteiropromo.core.interestPoint.domain.entities;


import ifpr.roteiropromo.core.addres.model.entities.Address;
import ifpr.roteiropromo.core.enums.InterestPointType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Data
public abstract class InterestPoint {


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id;

    String name;

    @OneToOne(cascade = CascadeType.ALL)
    Address address;

    Integer averageValue;

    String shortDescription;

    @Enumerated(EnumType.STRING)
    InterestPointType interestPointType;

}
