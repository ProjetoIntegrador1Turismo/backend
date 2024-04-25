package ifpr.roteiropromo.core.review.domain.entities;

import ifpr.roteiropromo.core.guideprofile.domain.entities.GuideProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private String date;
    private Integer rating;
    private String touristName;

    @ManyToOne
    private GuideProfile guideProfile;



}
