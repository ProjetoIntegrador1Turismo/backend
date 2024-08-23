package ifpr.roteiropromo.core.comments.controller;


import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTO;
import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTOForm;
import ifpr.roteiropromo.core.comments.service.CommentService;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create/{interestPointId}")
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable Long interestPointId,
            @RequestBody CommentDTOForm commentDTOForm){
        return ResponseEntity.ok(commentService.createComment(interestPointId, commentDTOForm));
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAll(){
        return ResponseEntity.ok(commentService.getAll());
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getOne(@PathVariable Long commentId){
        return ResponseEntity.ok(commentService.getOne(commentId));
    }

    @GetMapping("/interestpoint/{interestPointId}")
    public ResponseEntity<List<CommentDTO>> getAllByInterestPoint(@PathVariable Long interestPointId){
        return ResponseEntity.ok(commentService.getAllByInterestPoint(interestPointId));
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }


}
