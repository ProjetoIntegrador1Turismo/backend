package ifpr.roteiropromo.core.interestPoint.repository;

import ifpr.roteiropromo.core.interestPoint.domain.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Page<Hotel> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
