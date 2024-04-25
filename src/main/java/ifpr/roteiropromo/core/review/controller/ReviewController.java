package ifpr.roteiropromo.core.review.controller;


import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTO;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTOForm;
import ifpr.roteiropromo.core.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{guideId}/all")
    public ResponseEntity<List<ReviewDTO>> getAllByGuide(@PathVariable Long guideId){
        return ResponseEntity.ok(reviewService.getAllByGuide(guideId));
    }

    @PostMapping("/{touristId}/create/{guideId}")
    public ResponseEntity<ReviewDTO> createReview(@PathVariable Long guideId, @RequestBody ReviewDTOForm reviewDTOForm, @PathVariable Long touristId){
        return ResponseEntity.ok(reviewService.createReview(touristId, guideId, reviewDTOForm));
    }
}
