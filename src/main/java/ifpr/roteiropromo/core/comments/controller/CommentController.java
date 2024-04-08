package ifpr.roteiropromo.core.comments.controller;


import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTO;
import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTOForm;
import ifpr.roteiropromo.core.comments.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment/{touristId}")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/create/{interestPointId}")
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable Long interestPointId,
            @RequestBody CommentDTOForm commentDTOForm,
            @PathVariable Long touristId){
        return ResponseEntity.ok(commentService.createComment(touristId, interestPointId, commentDTOForm));
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAll(){
        return ResponseEntity.ok(commentService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<CommentDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(commentService.getOne(id));
    }


}
