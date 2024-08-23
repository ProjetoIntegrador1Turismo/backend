package ifpr.roteiropromo.core.user.repository;

import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TouristRepository extends JpaRepository<Tourist, Long> {

    List<Tourist> findAllByCommentsInterestPointId(Long interestPointId);




}
