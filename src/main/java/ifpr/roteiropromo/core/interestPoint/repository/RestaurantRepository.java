package ifpr.roteiropromo.core.interestPoint.repository;

import ifpr.roteiropromo.core.interestPoint.domain.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
    Page<Restaurant> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
