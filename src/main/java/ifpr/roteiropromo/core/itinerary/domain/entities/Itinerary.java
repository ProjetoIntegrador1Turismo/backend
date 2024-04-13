package ifpr.roteiropromo.core.itinerary.domain.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ifpr.roteiropromo.core.guideprofile.domain.entities.GuideProfile;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
public class Itinerary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private float mediumCost;

    private Integer days;

    @ManyToMany
    private List<InterestPoint> interestPoints;

    @ManyToOne
    @JoinColumn(name = "guideProfile_id")
    @JsonManagedReference
    private GuideProfile guideProfile;
}
