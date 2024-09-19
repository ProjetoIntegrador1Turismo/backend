package ifpr.roteiropromo.core.review.controller;


import ifpr.roteiropromo.core.errors.StandardError;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTO;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTOForm;
import ifpr.roteiropromo.core.review.service.ReviewService;
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


@RestController
@RequestMapping("/review")
@Tag(name = "Review", description = "Operations related to reviews.")
public class ReviewController {

    private final ReviewService reviewService;



    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Review one guide",
            description = "Allow Tourist to create a review to one guide",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Guide not found to review with id provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }),
            @ApiResponse(responseCode = "400", description = "Tourist already reviewed this guide",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class))})
    })
    public ResponseEntity<ReviewDTO> reviewOneGuide(@PathVariable Long id, @RequestBody ReviewDTOForm reviewDTOForm) {
        return ResponseEntity.ok(reviewService.reviewOneGuide(id, reviewDTOForm));
    }

    @PutMapping()
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update review",
            description = "Allow Tourist to update a review",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Not found review to update with id provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class))})
    })
    public ResponseEntity<ReviewDTO> updateReview(@RequestBody ReviewDTO reviewDto) {
        return ResponseEntity.ok(reviewService.updateReview(reviewDto));
    }

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete a review",
            description = "Allow Tourist to delete a review by id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review deleted successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Not found review to delete with id provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class))})
    })
    public ResponseEntity<Void> deleteOneReview(@PathVariable Long reviewId){
        reviewService.deleteReviewById(reviewId);
        return ResponseEntity.noContent().build();
    }


}
