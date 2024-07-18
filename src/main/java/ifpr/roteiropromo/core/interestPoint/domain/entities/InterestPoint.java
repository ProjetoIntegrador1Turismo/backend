package ifpr.roteiropromo.core.interestPoint.domain.entities;


import ifpr.roteiropromo.core.address.model.entities.Address;
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
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private Integer averageValue;

    private String shortDescription;

    private String imageCoverUrl;

    @Enumerated(EnumType.STRING)
    private InterestPointType interestPointType;

}
