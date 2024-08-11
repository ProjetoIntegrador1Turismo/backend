package ifpr.roteiropromo.core.interestPoint.repository;

import ifpr.roteiropromo.core.interestPoint.domain.entities.Experience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long>{
    Page<Experience> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
