package ifpr.roteiropromo.core.comments.service;

import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTO;
import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTOForm;
import ifpr.roteiropromo.core.comments.domain.entities.Comment;
import ifpr.roteiropromo.core.comments.repository.CommentRepository;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.repository.InterestPointRepository;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ModelMapper mapper;
    private final InterestPointService interestPointService;
    private final CommentRepository commentRepository;
    private final UserService userService;

    public CommentDTO createComment(Long touristId, Long interestPointId, CommentDTOForm commentDTOForm) {
        InterestPoint interestPointFound = interestPointService.getOne(interestPointId);
        Tourist touristFound = userService.getTouristById(touristId);
        Comment newComment = mapper.map(commentDTOForm, Comment.class);
        newComment.setInterestPoint(interestPointFound);
        Comment commentSave = commentRepository.save(newComment);
        touristFound.getComment().add(commentSave);
        userService.updateTourist(touristFound);
        return mapper.map(commentSave, CommentDTO.class);
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
}
