package ifpr.roteiropromo.core.interestPoint.domain.entities;


import ifpr.roteiropromo.core.address.model.entities.Address;
import ifpr.roteiropromo.core.enums.InterestPointType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Data
public abstract class InterestPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    //MUDAR VALOR PARA LONG
    private Integer averageValue;
    private String shortDescription;
    private String imageCoverUrl;

    @Enumerated(EnumType.STRING)
    private InterestPointType interestPointType;
}
