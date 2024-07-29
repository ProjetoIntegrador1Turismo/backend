package ifpr.roteiropromo.core.review.controller;


import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTO;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTOForm;
import ifpr.roteiropromo.core.review.service.ReviewService;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
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

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewDTO> reviewOneGuide(@RequestBody ReviewDTOForm reviewDTOForm) {
        return ResponseEntity.ok(reviewService.reviewOneGuide(reviewDTOForm));
    }

    @PutMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewDTO> updateRating(@RequestBody ReviewDTO reviewDto) {
        return ResponseEntity.ok(reviewService.updateReview(reviewDto));
    }



}
