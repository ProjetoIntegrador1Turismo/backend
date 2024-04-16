package ifpr.roteiropromo.core.user.repository;

import ifpr.roteiropromo.core.user.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

    User getOnByEmail(String email);

    Boolean existsUserByEmail(String email);

}
