package ifpr.roteiropromo.core.user.domain.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tourist extends User{

    private String comments;

}
