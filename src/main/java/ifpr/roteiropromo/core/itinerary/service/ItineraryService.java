package ifpr.roteiropromo.core.itinerary.service;

import ifpr.roteiropromo.core.auth.domain.AuthenticatedUserDTO;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTOForm;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryResponseDTO;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryUpdateDTO;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.itinerary.repository.ItineraryRepository;
import ifpr.roteiropromo.core.user.domain.dtos.GuideDTO;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.repository.GuideRepository;
import ifpr.roteiropromo.core.user.repository.TouristRepository;
import ifpr.roteiropromo.core.user.repository.UserRepository;
import ifpr.roteiropromo.core.user.service.UserService;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ItineraryService {

    private final ModelMapper modelMapper;
    private final ItineraryRepository itineraryRepository;
    private final InterestPointService interestPointService;
    private final JwtTokenHandler jwtTokenHandler;
    private final GuideRepository guideRepository;
    private final UserRepository userRepository;
    private final TouristRepository touristRepository;
    private final UserService userService;


    public ItineraryDTO create(ItineraryDTOForm itineraryDTOForm) {

        final String DEFAULT_IMAGE_URL = "http://localhost:8081/uploads/interestpointplaceholder.webp";

        Guide guideFound = getGuideAuthenticated();
        Itinerary newItinerary = fillItineraryDataAndPoints(itineraryDTOForm);

        newItinerary.setImageCoverUrl(DEFAULT_IMAGE_URL);

        Itinerary itinerarySaved = itineraryRepository.save(newItinerary);
        guideFound.getItineraries().add(itinerarySaved);
        userRepository.save(guideFound);
        return modelMapper.map(itinerarySaved, ItineraryDTO.class);
    }

    private Itinerary fillItineraryDataAndPoints(ItineraryDTOForm itineraryDTOForm) {
        Itinerary itinerary = modelMapper.map(itineraryDTOForm, Itinerary.class);
        List<InterestPoint> interestPoints = getInterestPoints(itineraryDTOForm.getInterestPointsId());
        itinerary.setInterestPoints(interestPoints);
        return itinerary;
    }

    private List<InterestPoint> getInterestPoints(List<Long> interestPointsId) {
        List<InterestPoint> interestPoints = new ArrayList<>();
        for (Long interestId : interestPointsId){
            interestPoints.add(interestPointService.getOne(interestId));
        }
        return interestPoints;
    }

    public ItineraryDTO update(Long id, ItineraryUpdateDTO itineraryDTO) {

        Guide guide = getGuideAuthenticated();
        Itinerary itinerary = getOneItineraryFromGuide(guide, id);

        modelMapper.map(itineraryDTO, itinerary);
        Itinerary itineraryUpdated = updateItineraryInterestPoints(itinerary, itineraryDTO.getInterestPointsId());
        return modelMapper.map(itineraryRepository.save(itineraryUpdated), ItineraryDTO.class);
    }


    private Itinerary updateItineraryInterestPoints(Itinerary itinerary, List<Long> interestPointsId) {
        itinerary.getInterestPoints().clear();

        itinerary.setInterestPoints(getInterestPoints(interestPointsId));
        Itinerary itineraryUpdate = itineraryRepository.save(itinerary);
        itineraryUpdate.setInterestPoints(getInterestPoints(interestPointsId));
        return itineraryUpdate;
    }

    public ItineraryDTO addInterestPoint(Long itineraryId, Long interestPointId) {
        Guide guide = getGuideAuthenticated();
        Itinerary itineraryFound = getOneItineraryFromGuide(guide, itineraryId);
        InterestPoint interestPointFound = interestPointService.getOne(interestPointId);
        itineraryFound.getInterestPoints().add(interestPointFound);
        return modelMapper.map(itineraryRepository.save(itineraryFound), ItineraryDTO.class);
    }

    public Itinerary getOneItineraryFromGuide(Guide guide, Long id){
        return guide.getItineraries().stream().filter(t->t.getId().equals(id)).findFirst().orElseThrow(
                () -> new ServiceError("O guia autenticado não possui um roteiro com o ID: " + id)
        );
    }

    private Guide getGuideAuthenticated() {
        AuthenticatedUserDTO userDTO = jwtTokenHandler.getUserDataFromToken();
        String userEmail = userDTO.getEmail();
        if (userEmail == null || userEmail.isEmpty()) {
            throw new ServiceError("User email not found in token");
        }
        Guide guide = (Guide) userRepository.getOneByEmail(userEmail);
        if (guide == null) {
            throw new ServiceError("Guide not found with email: " + userEmail);
        }

        return guide;
    }

    public Itinerary findOneById(Long id){
        return itineraryRepository.findById(id).orElseThrow(
                () -> new ServiceError("Não foi possível encontrar um roteiro com o ID: " + id)
        );
    }

    public List<ItineraryDTO> findAll() {
        return itineraryRepository.findAll().stream()
                .map(itinerary -> {
                    return modelMapper.map(itinerary, ItineraryDTO.class);
                })
                .collect(Collectors.toList());
    }


    public ItineraryDTO findById(Long id) {
        return modelMapper.map(findOneById(id), ItineraryDTO.class);
    }

    public ItineraryResponseDTO getGuideByItinerary(Long id) {
        Itinerary itinerary = findOneById(id);
        Guide guide = guideRepository.getByItineraries(itinerary);
        ItineraryResponseDTO itineraryResponseDTO = modelMapper.map(itinerary, ItineraryResponseDTO.class);
        itineraryResponseDTO.setGuide(modelMapper.map(guide, GuideDTO.class));
        return itineraryResponseDTO;
    }

    public void delete(Long id) {
        Guide guide = getGuideAuthenticated();
        Itinerary itinerary = getOneItineraryFromGuide(guide, id);
        removeItineraryFromTouristHaveSetSignal(itinerary);
        guide.getItineraries().remove(itinerary);
        guideRepository.save(guide);
        itineraryRepository.delete(itinerary);
    }

    private void removeItineraryFromTouristHaveSetSignal(Itinerary itinerary) {
        List<Tourist> tourists = touristRepository.findAll();
        for (Tourist tourist : tourists){
            if(tourist.getInterestedItineraries().contains(itinerary)){
                tourist.getInterestedItineraries().remove(itinerary);
                touristRepository.save(tourist);
            }
        }
    }

    //Method to delete the itinerary from the tourists who have it in their favorites


    public void updateCoverImageUrl(Long id, String imageUrl) {
        Itinerary itinerary = findOneById(id);
        itinerary.setImageCoverUrl(imageUrl);
        itineraryRepository.save(itinerary);
    }


    public List<ItineraryDTO> getByGuideEmail(String email){
        Guide guideFound = (Guide) userService.getOneByEmail(email);
        return guideFound.getItineraries().stream()
                .map(itinerary -> {
                    return modelMapper.map(itinerary, ItineraryDTO.class);}).collect(Collectors.toList());
        }

    public List<ItineraryDTO> getItinerariesByGuideAndInterestPoint(Long guideId, Long interestPointId) {
        Guide guide = userService.findGuideById(guideId);
        if (guide == null) {
            throw new ServiceError("Guide not found");
        }

        return guide.getItineraries().stream()
                .filter(itinerary -> itinerary.getInterestPoints().stream()
                        .anyMatch(interestPoint -> interestPoint.getId().equals(interestPointId)))
                .map(itinerary -> modelMapper.map(itinerary, ItineraryDTO.class))
                .collect(Collectors.toList());
    }
}



