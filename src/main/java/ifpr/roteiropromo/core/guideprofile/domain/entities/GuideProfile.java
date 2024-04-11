package ifpr.roteiropromo.core.guideprofile.domain.entities;


import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
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
public class GuideProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String contact;
    private String description;
    private String cadastur;

    @OneToMany
    private List<Itinerary> itineraries;

//    @ManyToOne
//    private List<Avaliacao> avaliacoes;

}
