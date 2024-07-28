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
import ifpr.roteiropromo.core.user.repository.GuideRepository;
import ifpr.roteiropromo.core.user.repository.UserRepository;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final ModelMapper modelMapper;
    private final ItineraryRepository itineraryRepository;
    private final InterestPointService interestPointService;
    private final JwtTokenHandler jwtTokenHandler;
    private final GuideRepository guideRepository;
    private final UserRepository userRepository;

    public ItineraryDTO create(ItineraryDTOForm itineraryDTOForm) {
        Guide guideFound = getGuideAuthenticated();
        if (guideFound == null) {
            throw new ServiceError("Guia não encontrado");
        }

        Itinerary newItinerary = modelMapper.map(itineraryDTOForm, Itinerary.class);
        Itinerary itinerarySaved = itineraryRepository.save(newItinerary);
        guideFound.getItineraries().add(itinerarySaved);
        userRepository.save(guideFound);
        return modelMapper.map(itinerarySaved, ItineraryDTO.class);
    }

    public ItineraryDTO update(Long id, ItineraryUpdateDTO itineraryDTO) {
        Guide guide = getGuideAuthenticated();
        Itinerary itinerary = guide.getItineraries().stream().filter(t->t.getId().equals(id)).findFirst().orElseThrow(
                () -> new ServiceError("O guia autenticado não possui um roteiro com o ID: " + id)
        );
        modelMapper.map(itineraryDTO, itinerary);
        return modelMapper.map(itineraryRepository.save(itinerary), ItineraryDTO.class);
    }

    public ItineraryDTO addInterestPoint(Long itineraryId, Long interestPointId) {
        Guide guide = getGuideAuthenticated();
        Itinerary itineraryFound = getOneItineraryFromGuide(guide, itineraryId);
        //Duplicated method!
        //InterestPoint interestPointFound = interestPointService.findById(interestPointIds);
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
        guide.getItineraries().remove(itinerary);
        guideRepository.save(guide);
        itineraryRepository.delete(itinerary);
    }


    public void updateCoverImageUrl(Long id, String imageUrl) {
        Itinerary itinerary = findOneById(id);
        itinerary.setImageCoverUrl(imageUrl);
        itineraryRepository.save(itinerary);
    }

}
