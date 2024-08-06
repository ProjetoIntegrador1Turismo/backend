package ifpr.roteiropromo.core.interestPoint.domain.entities;


import ifpr.roteiropromo.core.address.model.entities.Address;
import ifpr.roteiropromo.core.enums.InterestPointType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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


    private Float averageValue;
    private String shortDescription;
    private String imageCoverUrl;
    @ElementCollection
    private List<String> images;

    @Enumerated(EnumType.STRING)
    private InterestPointType interestPointType;
}
