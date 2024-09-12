package ifpr.roteiropromo.core.pagesource.service;

import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.pagesource.domain.BasicItineraryDTO;
import ifpr.roteiropromo.core.pagesource.domain.GuideProfileDTO;
import ifpr.roteiropromo.core.pagesource.domain.GuideReviewDTO;
import ifpr.roteiropromo.core.pagesource.domain.InterestedTouristDTO;
import ifpr.roteiropromo.core.review.service.ReviewService;
import ifpr.roteiropromo.core.user.domain.dtos.SimpleTouristDTO;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<InterestedTouristDTO> interestedTourists = this.getInterestedTourists(id);
        guideProfileDTO.setReviews(guideReviewDTOS);
        guideProfileDTO.setInterested(interestedTourists);
        return guideProfileDTO;
    }


    public List<InterestedTouristDTO> getInterestedTourists(long id){
        Guide guide = userService.findGuideById(id);
        List<InterestedTouristDTO> interestedTourists = new ArrayList<>();

        for (Itinerary itinerary : guide.getItineraries()) {
            for (Tourist tourist : itinerary.getInterestedTourists()) {
                InterestedTouristDTO interestedTouristDTO = new InterestedTouristDTO();
                interestedTouristDTO.setTourist(mapper.map(tourist, SimpleTouristDTO.class));
                BasicItineraryDTO basicItineraryDTO = mapper.map(itinerary, BasicItineraryDTO.class);
                interestedTouristDTO.setItinerary(basicItineraryDTO);
                interestedTourists.add(interestedTouristDTO);
            }
        }
        return interestedTourists;
    }


}
