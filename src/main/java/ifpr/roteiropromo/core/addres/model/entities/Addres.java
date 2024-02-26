package ifpr.roteiropromo.core.addres.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Addres {

    @Id
    @Column(unique = true)
    Long id;
    String road;
    Integer number;
    Integer zipCode;

}
