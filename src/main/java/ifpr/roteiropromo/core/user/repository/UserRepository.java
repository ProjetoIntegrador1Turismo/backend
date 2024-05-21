package ifpr.roteiropromo.core.user.repository;

import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{

    User getOnByEmail(String email);

    Boolean existsUserByEmail(String email);

    @Query("SELECT g FROM Guide g")
    List<Guide> findAllGuides();
}
