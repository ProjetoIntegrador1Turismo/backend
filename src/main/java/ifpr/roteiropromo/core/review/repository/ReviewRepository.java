package ifpr.roteiropromo.core.review.repository;

import ifpr.roteiropromo.core.review.domain.entities.Review;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
//    List<Review> findByGuideId(Long guideId);
//    boolean existsByGuideIdAndTourist(long guideId);
//    Optional<Review> findByGuideIdAndTourist(long guideId);

    boolean existsByGuideId(Long guideId);
    List<Review> findByGuideId(Long guideId);

}