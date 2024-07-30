package ifpr.roteiropromo.core.review.service;

import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTO;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTOForm;
import ifpr.roteiropromo.core.review.domain.entities.Review;
import ifpr.roteiropromo.core.review.repository.ReviewRepository;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.service.UserService;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewService {

    private final ModelMapper mapper;
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final JwtTokenHandler jwtTokenHandler;

    public ReviewDTO reviewOneGuide(ReviewDTOForm reviewDTOForm) {
        Tourist tourist = getTouristAuthenticated();
        Guide guideToReview = getGuideToReview(reviewDTOForm.getGuideEmail());
        validateReview(reviewDTOForm, tourist, guideToReview.getId());
        Review review = new Review();
        mapper.map(reviewDTOForm, review);
        review.setGuideId(guideToReview.getId());
        tourist.getReviews().add(review);
        userService.updateTourist(tourist);
        return mapper.map(reviewRepository.save(review), ReviewDTO.class);
    }

    public ReviewDTO updateReview(ReviewDTO reviewDTO) {
        Tourist tourist = getTouristAuthenticated();
        Review reviewToUpdate = getReviewFromTourist(tourist, reviewDTO.getId());
        mapper.map(reviewDTO, reviewToUpdate);
        return mapper.map(reviewRepository.save(reviewToUpdate), ReviewDTO.class);
    }

    private Review getReviewFromTourist(Tourist tourist, Long id) {
        Review reviewToUpdate = null;
        for (Review review: tourist.getReviews()) {
            if (review.getId().equals(id)){
                reviewToUpdate = review;
            }
        }
        if (reviewToUpdate != null){
            return reviewToUpdate;
        }else {
            throw new ServiceError("Authenticated Tourist dont have one review with id: " + id);
        }
    }

    private Review getReviewByIdOrFail(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ServiceError("Review not found with id: " + id));
    }


    private void validateReview(ReviewDTOForm reviewDTOForm, Tourist tourist, Long guideId) {
        if (reviewDTOForm.getRating() < 1 || reviewDTOForm.getRating() > 5) {
            throw new ServiceError("Rating must be between 1 and 5.");
        }

        if(tourist.getReviews().stream().anyMatch(r -> r.getGuideId().equals(guideId))){
            throw new ServiceError("Authenticated tourist has already created a review for this guide.");
        }
    }

    private Guide getGuideToReview(String email) {
        User userFound = userService.getOneByEmail(email);
        if(!(userFound instanceof Guide)){
            throw new ServiceError("This email does not belong to a Guide (you can only review Guides): " + email);
        }
        return mapper.map(userFound, Guide.class);
    }

    private Tourist getTouristAuthenticated(){
        AuthenticatedUserDTO authenticatedUser = jwtTokenHandler.getUserDataFromToken();
        User userFound = userService.getOneByEmail(authenticatedUser.getEmail());
        if(!(userFound instanceof Tourist)){
            throw new ServiceError("Authenticated user cannot create reviews (not Tourist type)");
        }
        return mapper.map(userFound, Tourist.class);
    }


    public void deleteReviewById(Long reviewId) {
        Tourist tourist = getTouristAuthenticated();
        Review review = getReviewFromTourist(tourist, reviewId);
        tourist.getReviews().remove(review);
        userService.updateTourist(tourist);
    }
}
