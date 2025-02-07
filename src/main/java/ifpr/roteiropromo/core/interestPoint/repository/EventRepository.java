package ifpr.roteiropromo.core.interestPoint.repository;

import ifpr.roteiropromo.core.interestPoint.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
