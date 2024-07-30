package ifpr.roteiropromo.core.user.service;

import ifpr.roteiropromo.core.pagesource.domain.TopGuideDTO;
import ifpr.roteiropromo.core.review.domain.entities.Review;
import ifpr.roteiropromo.core.review.repository.ReviewRepository;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.repository.GuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;
    private final ReviewRepository reviewRepository;



    public List<Long> getTopFiveGuidesId(){
        List<Review> reviews = reviewRepository.findAll();
        Map<Long, Double> averageRatings = reviews.stream()
                .collect(Collectors.groupingBy(
                        Review::getGuideId,
                        Collectors.averagingInt(Review::getRating)
                ));

        List<Long> topRatedGuideIds = averageRatings.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return topRatedGuideIds;
    }


    private double calculateAverageRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        int sum = reviews.stream().mapToInt(Review::getRating).sum();
        return (double) sum / reviews.size();
    }


    public List<Guide> getAllGuides() {
        return new ArrayList<>(guideRepository.findAll());
    }
}
