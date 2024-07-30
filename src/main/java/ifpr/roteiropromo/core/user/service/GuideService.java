package ifpr.roteiropromo.core.user.service;

import ifpr.roteiropromo.core.pagesource.domain.TopGuideDTO;
import ifpr.roteiropromo.core.review.domain.entities.Review;
import ifpr.roteiropromo.core.review.repository.ReviewRepository;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.repository.GuideRepository;
import ifpr.roteiropromo.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;
    private final ReviewRepository reviewRepository;

    public List<TopGuideDTO> getTopGuidesDTO(){
        Map<Long, Double> topGuides = getTopFiveGuidesId();
        List<TopGuideDTO> topGuidesList = new ArrayList<>();
        topGuides.forEach((guideId, rateValue) -> {
            buildTopGuideDTO(topGuidesList, guideId, rateValue);
        });
        return topGuidesList;
    }

    private void buildTopGuideDTO(List<TopGuideDTO> topGuidesList, Long guideId, Double rateValue) {
        Guide guide = guideRepository.getOneById(guideId);
        if (guide != null){
            TopGuideDTO topGuideDTO = new TopGuideDTO();
            topGuideDTO.setFirstName(guide.getFirstName());
            topGuideDTO.setId(guide.getId());
            topGuideDTO.setAverageRating(rateValue);
            topGuidesList.add(topGuideDTO);
        }
    }


    public Map<Long, Double> getTopFiveGuidesId(){
        List<Review> reviews = reviewRepository.findAll();
        return orderRatingsAndGetTopFive(calculateAverageRatingForEachGuide(reviews));
    }

    private Map<Long, Double> orderRatingsAndGetTopFive(Map<Long, Double> averageRatings) {
        return averageRatings.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private Map<Long, Double> calculateAverageRatingForEachGuide(List<Review> reviews) {
        return reviews.stream()
                .collect(Collectors.groupingBy(
                        Review::getGuideId,
                        Collectors.averagingInt(Review::getRating)
                ));
    }

    public List<Guide> getAllGuides() {
        return new ArrayList<>(guideRepository.findAll());
    }
}
