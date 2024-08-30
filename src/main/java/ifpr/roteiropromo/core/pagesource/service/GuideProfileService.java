package ifpr.roteiropromo.core.pagesource.service;

import ifpr.roteiropromo.core.pagesource.domain.GuideProfileDTO;
import ifpr.roteiropromo.core.pagesource.domain.GuideReviewDTO;
import ifpr.roteiropromo.core.review.service.ReviewService;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuideProfileService {

    private final UserService userService;
    private final ModelMapper mapper;
    private final ReviewService reviewService;

    public GuideProfileDTO getProfileGuideData(long id){
        Guide guide = userService.findGuideById(id);
        GuideProfileDTO guideProfileDTO = mapper.map(guide, GuideProfileDTO.class);
        List<GuideReviewDTO> guideReviewDTOS = reviewService.getAllByGuide(id);
        guideProfileDTO.setReviews(guideReviewDTOS);
        return guideProfileDTO;
    }

}
