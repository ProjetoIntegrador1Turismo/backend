package ifpr.roteiropromo.core.user.service;

import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.pagesource.domain.TopGuideDTO;
import ifpr.roteiropromo.core.user.domain.dtos.GuideDTO;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.repository.GuideRepository;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;
    private final JwtTokenHandler jwtTokenHandler;
    private final ModelMapper mapper;

    public List<TopGuideDTO> getTopGuidesDTO(){
        List<Guide> guides = guideRepository.getGuidesOrderedByRating();
        List<TopGuideDTO> topGuideDTOS = new ArrayList<>();
        for (int i = 0; i < Math.min(3, guides.size()); i++) {
            topGuideDTOS.add(mapper.map(guides.get(i), TopGuideDTO.class));
        }
        return topGuideDTOS;
    }


    public List<Guide> getGuidesWhoOfferTour(Long id){
         return guideRepository.findGuidesByInterestPoint(id);
    }


    public List<GuideDTO> getAllGuides() {
        return new ArrayList<>(guideRepository.findAll().stream().map(guide -> mapper.map(guide, GuideDTO.class)).collect(Collectors.toList()));
    }

    public List<ItineraryDTO> getItinerariesFromAuthenticatedGuide() {
        AuthenticatedUserDTO guideAuthenticated = jwtTokenHandler.getUserDataFromToken();
        Guide guide = guideRepository.getOnByEmail(guideAuthenticated.getEmail());
        return guide.getItineraries().stream().map(itinerary -> mapper.map(itinerary, ItineraryDTO.class)).collect(Collectors.toList());
    }

    public void updateGuide(Guide guide) {
        guideRepository.save(guide);
    }
}
