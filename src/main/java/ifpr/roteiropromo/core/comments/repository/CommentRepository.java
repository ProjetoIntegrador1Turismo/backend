package ifpr.roteiropromo.core.comments.repository;

import ifpr.roteiropromo.core.comments.domain.entities.Comment;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByInterestPoint(InterestPoint interestPointFound);
}
