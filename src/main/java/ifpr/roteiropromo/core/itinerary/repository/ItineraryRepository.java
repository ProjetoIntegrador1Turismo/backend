package ifpr.roteiropromo.core.itinerary.repository;

import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {


    Page<Itinerary> findItinerariesByTitleContainingIgnoreCase(String query, Pageable pageable);
}