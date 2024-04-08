package ifpr.roteiropromo.core.comments.domain.entities;

import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private String wasVisitingDate;
    private Integer rating;
    @OneToOne()
    private InterestPoint interestPoint;

}
