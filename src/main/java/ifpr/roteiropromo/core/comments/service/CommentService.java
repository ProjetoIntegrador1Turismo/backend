package ifpr.roteiropromo.core.comments.service;

import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTO;
import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTOForm;
import ifpr.roteiropromo.core.comments.domain.entities.Comment;
import ifpr.roteiropromo.core.comments.repository.CommentRepository;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.repository.InterestPointRepository;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTOForm;
import ifpr.roteiropromo.core.review.domain.entities.Review;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.repository.TouristRepository;
import ifpr.roteiropromo.core.user.service.UserService;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentService {

    private final ModelMapper mapper;
    private final InterestPointService interestPointService;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final JwtTokenHandler jwtTokenHandler;
    private final TouristRepository touristRepository;
    private final InterestPointRepository interestPointRepository;

    public CommentDTO createComment(Long interestPointId, CommentDTOForm commentDTOForm) {
        Tourist tourist = getTouristAuthenticated();
        validateComment(commentDTOForm, tourist, interestPointId);
        InterestPoint interestPointFound = interestPointService.getOne(interestPointId);
        Comment newComment = mapper.map(commentDTOForm, Comment.class);
        newComment.setInterestPoint(interestPointFound);
        newComment.setTouristName(tourist.getFirstName() + " " + tourist.getLastName());
        Comment commentSave = commentRepository.save(newComment);
        tourist.getComments().add(commentSave);
        userService.updateTourist(tourist);
        updateInterestPointAverageRating(interestPointFound);
        return mapper.map(commentSave, CommentDTO.class);
    }

    private void updateInterestPointAverageRating(InterestPoint interestPointFound) {
        List<Comment> comments = commentRepository.findAllByInterestPoint(interestPointFound);
        interestPointFound.setAverageRating(calculateAverageRating(comments));
        interestPointRepository.save(interestPointFound);
    }

    private Integer calculateAverageRating(List<Comment> comments) {
        Integer ratings = 0;
        for (Comment comment : comments){
            ratings += comment.getRating();
        }
        return ratings / comments.size();
    }

    private void validateComment(CommentDTOForm commentDTOForm, Tourist tourist, Long interestPointId) {
        if (commentDTOForm.getRating() < 1 || commentDTOForm.getRating() > 5) {
            throw new ServiceError("Rating must be between 1 and 5.");
        }

        if(tourist.getComments().stream().anyMatch(r -> r.getInterestPoint().getId().equals(interestPointId))){
            throw new ServiceError("Authenticated tourist has already created a review for Interest Point.");
        }
    }



    private Tourist getTouristAuthenticated() {
        AuthenticatedUserDTO authenticatedUserDTO = jwtTokenHandler.getUserDataFromToken();
        User user = userService.getOneByEmail(authenticatedUserDTO.getEmail());
        if (!(user instanceof Tourist)){
            throw new ServiceError("Authenticated user cannot create reviews (not Tourist type)");
        }
        return mapper.map(user, Tourist.class);
    }

    public List<CommentDTO> getAll(){
        return commentRepository.findAll().stream().map(comment -> mapper.map(comment, CommentDTO.class)).toList();
    }

    public CommentDTO getOne(Long id) {
        Comment commentFound = commentRepository.findById(id).orElseThrow(
                () -> new ServiceError("Could not find comment with id: " + id)
        );
        return mapper.map(commentFound, CommentDTO.class);
    }


    public List<CommentDTO> getAllByInterestPoint(Long interestPointId) {
        InterestPoint interestPointFound = interestPointService.getOne(interestPointId);
        return commentRepository.findAllByInterestPoint(interestPointFound).stream().map(
                comment -> mapper.map(comment, CommentDTO.class)
        ).toList();
    }

    public void deleteComment(Long commentId) {
        Tourist tourist = getTouristAuthenticated();
        Comment comment = getCommentFromTouristOrFail(tourist, commentId);
        tourist.getComments().remove(comment);
        userService.updateTourist(tourist);
    }

    private Comment getCommentFromTouristOrFail(Tourist tourist, Long commentId) {
        Comment commentToDelete = null;
        for (Comment comment : tourist.getComments()){
            if (comment.getId().equals(commentId)){
                commentToDelete = comment;
            }
        }

        if (commentToDelete != null){
            return commentToDelete;
        }else {
            throw new ServiceError("Tourist authenticated dont have a comment with id: " + commentId);
        }
    }


}
