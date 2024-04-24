package ifpr.roteiropromo.core.review.repository;

import ifpr.roteiropromo.core.guideprofile.domain.entities.GuideProfile;
import ifpr.roteiropromo.core.review.domain.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Collection<Object> findAllByGuideProfile(GuideProfile guideProfileFound);
}
