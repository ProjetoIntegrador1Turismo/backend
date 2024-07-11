package ifpr.roteiropromo.core.user.repository;

import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<Guide, Long> {

    Guide getOnByEmail(String email);

    Guide getByItineraries(Itinerary itinerary);

}
