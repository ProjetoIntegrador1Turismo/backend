package ifpr.roteiropromo.core.comments.controller;


import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTO;
import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTOForm;
import ifpr.roteiropromo.core.comments.service.CommentService;
import ifpr.roteiropromo.core.errors.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@Tag(name = "Comment", description = "Operations related to Comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create/{interestPointId}")
    @Operation(summary = "Create new Comment for Interest Point",
            description = "Allow a Tourist to create a new comment for a specific Interest Point.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Interest Point not exist by id provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }),
            @ApiResponse(responseCode = "400", description = "Authenticated user is not a Tourist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }),
            @ApiResponse(responseCode = "400", description = "Tourist has already commented this Interest Point",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class))})
    })
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable Long interestPointId,
            @RequestBody CommentDTOForm commentDTOForm){
        return ResponseEntity.ok(commentService.createComment(interestPointId, commentDTOForm));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all Comments",
            description = "Retrieve all available comments.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDTO.class)) })
    })
    public ResponseEntity<List<CommentDTO>> getAll(){
        return ResponseEntity.ok(commentService.getAll());
    }

    @GetMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get a Comment by id",
            description = "Allow ADMIN user to retrieve one Comment by id.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Comment Not exist by id provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) })
    })
    public ResponseEntity<CommentDTO> getOne(@PathVariable Long commentId){
        return ResponseEntity.ok(commentService.getOne(commentId));
    }

    @GetMapping("/interestpoint/{interestPointId}")
    @Operation(summary = "Get all Comments by Interest Point",
            description = "Retrieve all available comments for a specific Interest Point.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Interest Point Not exist by id provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) })
    })
    public ResponseEntity<List<CommentDTO>> getAllByInterestPoint(@PathVariable Long interestPointId){
        return ResponseEntity.ok(commentService.getAllByInterestPoint(interestPointId));
    }

    @DeleteMapping("/delete/{commentId}")
    @Operation(summary = "Delete a Comment by id",
            description = "Allow Tourist user to delete one Comment by id.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Comment Not exist by id provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }),
            @ApiResponse(responseCode = "400", description = "Authenticated user is not a Tourist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) })
    })
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }


}
