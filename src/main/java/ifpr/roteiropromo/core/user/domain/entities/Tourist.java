package ifpr.roteiropromo.core.user.domain.entities;

import ifpr.roteiropromo.core.comments.domain.entities.Comment;
import ifpr.roteiropromo.core.review.domain.entities.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tourist extends User {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews;

    }