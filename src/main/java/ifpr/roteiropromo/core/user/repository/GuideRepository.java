package ifpr.roteiropromo.core.user.repository;

import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GuideRepository extends JpaRepository<Guide, Long> {

    Guide getOnByEmail(String email);

    Guide getByItineraries(Itinerary itinerary);

    Guide getOneById(Long id);

    @Query("SELECT g FROM Guide g JOIN g.itineraries i JOIN i.interestPoints ip WHERE ip.id = :interestPointId")
    List<Guide> findGuidesByInterestPoint(Long interestPointId);

}
