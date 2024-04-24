package ifpr.roteiropromo.core.review.service;

import ifpr.roteiropromo.core.guideprofile.domain.entities.GuideProfile;
import ifpr.roteiropromo.core.guideprofile.service.GuideProfileService;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTO;
import ifpr.roteiropromo.core.review.domain.DTO.ReviewDTOForm;
import ifpr.roteiropromo.core.review.domain.entities.Review;
import ifpr.roteiropromo.core.review.repository.ReviewRepository;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewService {

    private final ModelMapper mapper;
    private final ReviewRepository reviewRepository;
    private final GuideProfileService guideProfileService;
    private final UserService userService;

    public List<ReviewDTO> getAllByGuide(Long guideId){
        GuideProfile guideProfileFound = guideProfileService.findById(guideId);
        return reviewRepository.findAllByGuideProfile(guideProfileFound).stream().map(
                review -> mapper.map(review, ReviewDTO.class)
        ).toList();
    }

    public ReviewDTO createReview(Long touristId, Long guideId, ReviewDTOForm reviewDTOForm) {
        GuideProfile guideProfileFound = guideProfileService.findById(guideId);
        Review newReview = mapper.map(reviewDTOForm, Review.class);
        newReview.setGuideProfile(guideProfileFound);
        newReview.setTouristName(userService.getTouristById(touristId).getFirstName());
        Review reviewSave = reviewRepository.save(newReview);
        return mapper.map(reviewSave, ReviewDTO.class);
    }

}
