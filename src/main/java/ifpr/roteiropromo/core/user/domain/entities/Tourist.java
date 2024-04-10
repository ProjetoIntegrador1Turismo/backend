package ifpr.roteiropromo.core.user.domain.entities;

import ifpr.roteiropromo.core.comments.domain.entities.Comment;
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
public class Tourist extends User{

    @OneToMany
    private List<Comment> comment;

}
