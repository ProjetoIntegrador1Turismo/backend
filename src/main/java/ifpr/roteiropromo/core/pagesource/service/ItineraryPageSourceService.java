package ifpr.roteiropromo.core.pagesource.service;

import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.itinerary.service.ItineraryService;
import ifpr.roteiropromo.core.pagesource.domain.BasicGuideDTO;
import ifpr.roteiropromo.core.pagesource.domain.GuideReviewDTO;
import ifpr.roteiropromo.core.pagesource.domain.ItineraryPageDTO;
import ifpr.roteiropromo.core.review.service.ReviewService;
import ifpr.roteiropromo.core.user.service.GuideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItineraryPageSourceService {

    private final ItineraryService itineraryService;
    private final ReviewService reviewService;
    private final GuideService guideService;
    private final ModelMapper modelMapper;

    public ItineraryPageDTO getItineraryPageData(Long id){
        ItineraryPageDTO itineraryPageDTO = new ItineraryPageDTO();
        itineraryPageDTO.setItinerary(this.getItinerary(id));
        itineraryPageDTO.setReviews(this.getReviews(id));
        itineraryPageDTO.setGuide(this.getGuideByItineraryId(id));
        return itineraryPageDTO;
    }


    private ItineraryDTO getItinerary(Long id){
        return modelMapper.map(itineraryService.findOneById(id), ItineraryDTO.class);
    }

    private List<GuideReviewDTO> getReviews(Long id){
        Long guideId = itineraryService.getGuideByItinerary(id).getGuide().getId();
        List<GuideReviewDTO> reviews = reviewService.getAllByGuide(guideId);

        return reviews.stream()
                .limit(5)
                .collect(Collectors.toList());
    }

    private BasicGuideDTO getGuideByItineraryId(Long id){
        return modelMapper.map(itineraryService.getGuideByItinerary(id).getGuide(), BasicGuideDTO.class);
    }

}
