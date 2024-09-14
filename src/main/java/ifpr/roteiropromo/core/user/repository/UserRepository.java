package ifpr.roteiropromo.core.user.repository;

import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{

    User getOneByEmail(String email);

    Boolean existsUserByEmail(String email);

    @Query("SELECT g FROM Guide g")
    List<Guide> findAllGuides();

    @Query("SELECT g FROM Guide g WHERE g.isApproved = false")
    List<Guide> findAllUnapprovedGuides();

    @Query("SELECT g FROM Guide g WHERE g.isApproved = true")
    List<Guide> findAllApprovedGuides();
}
