package ifpr.roteiropromo.core.interestPoint.repository;

import ifpr.roteiropromo.core.interestPoint.domain.entities.TouristPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristPointRepository extends JpaRepository<TouristPoint, Long>{
}
