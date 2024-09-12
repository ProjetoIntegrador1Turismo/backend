package ifpr.roteiropromo.core.review.controller;


import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTO;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTOForm;
import ifpr.roteiropromo.core.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;



    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewDTO> reviewOneGuide(@PathVariable Long id, @RequestBody ReviewDTOForm reviewDTOForm) {
        return ResponseEntity.ok(reviewService.reviewOneGuide(id, reviewDTOForm));
    }

    @PutMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewDTO> updateReview(@RequestBody ReviewDTO reviewDto) {
        return ResponseEntity.ok(reviewService.updateReview(reviewDto));
    }

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteOneReview(@PathVariable Long reviewId){
        reviewService.deleteReviewById(reviewId);
        return ResponseEntity.noContent().build();
    }


}
