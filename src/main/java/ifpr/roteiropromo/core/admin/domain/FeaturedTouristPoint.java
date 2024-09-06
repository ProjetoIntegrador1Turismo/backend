package ifpr.roteiropromo.core.admin.domain;


import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FeaturedTouristPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "interest_point_id")
    private InterestPoint interestPoint;


}
