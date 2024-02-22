package ifpr.roteiropromo.core.itinerary.repository;

import ifpr.roteiropromo.core.itinerary.domain.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {}
