package ifpr.roteiropromo.core.itinerary.domain.entities;

import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
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
@Entity
public class Itinerary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    @Column(length = 2800)
    private String description;
    private float mediumCost;
    private Integer days;

    @ManyToMany
    private List<InterestPoint> interestPoints;

    private String imageCoverUrl; // Novo campo para URL da imagem

    @ManyToMany(mappedBy = "interestedItineraries")
    private List<Tourist> interestedTourists;

}


