package ifpr.roteiropromo.core.user.service;

import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.pagesource.domain.TopGuideDTO;
import ifpr.roteiropromo.core.review.domain.entities.Review;
import ifpr.roteiropromo.core.review.repository.ReviewRepository;
import ifpr.roteiropromo.core.user.domain.dtos.GuideDTO;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.repository.GuideRepository;
import ifpr.roteiropromo.core.user.repository.UserRepository;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
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
    private final JwtTokenHandler jwtTokenHandler;

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

    public List<Guide> getGuidesWhoOfferTour(Long id){
         return guideRepository.findGuidesByInterestPoint(id);
    }

    private Map<Long, Double> calculateAverageRatingForEachGuide(List<Review> reviews) {
        return reviews.stream()
                .collect(Collectors.groupingBy(
                        review -> review.getGuide().getId(),
                        Collectors.averagingInt(Review::getRating)
                ));
    }

    public List<Guide> getAllGuides() {
        return new ArrayList<>(guideRepository.findAll());
    }

    public List<Itinerary> getItinerariesFromAuthenticatedGuide() {
        AuthenticatedUserDTO guideAuthenticated = jwtTokenHandler.getUserDataFromToken();
        Guide guide = guideRepository.getOnByEmail(guideAuthenticated.getEmail());
        return guide.getItineraries();
    }

    public void updateGuide(Guide guide) {
        guideRepository.save(guide);
    }
}
