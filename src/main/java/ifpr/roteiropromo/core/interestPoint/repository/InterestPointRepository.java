package ifpr.roteiropromo.core.interestPoint.repository;

import ifpr.roteiropromo.core.enums.InterestPointType;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestPointRepository extends JpaRepository<InterestPoint, Long> {


    InterestPoint getOnByName(String name);


    List<InterestPoint> getAllByInterestPointType(String interestPointType);

}