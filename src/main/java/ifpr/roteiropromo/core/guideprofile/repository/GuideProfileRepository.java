package ifpr.roteiropromo.core.guideprofile.repository;


import ifpr.roteiropromo.core.guideprofile.domain.entities.GuideProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideProfileRepository extends JpaRepository<GuideProfile, Long> {
}
