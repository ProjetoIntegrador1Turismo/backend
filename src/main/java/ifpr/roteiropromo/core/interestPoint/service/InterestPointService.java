package ifpr.roteiropromo.core.interestPoint.service;


import ifpr.roteiropromo.core.address.model.dtos.AddressDTO;
import ifpr.roteiropromo.core.enums.InterestPointType;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTO;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointUpdateDTO;
import ifpr.roteiropromo.core.interestPoint.domain.entities.*;
import ifpr.roteiropromo.core.interestPoint.repository.InterestPointRepository;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.itinerary.repository.ItineraryRepository;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.repository.TouristRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class InterestPointService {

    private final ModelMapper modelMapper;
    private final InterestPointRepository interestPointRepository;
    private final ItineraryRepository itineraryRepository;
    private final TouristRepository touristRepository;


    public InterestPoint create(InterestPointDTOForm interestPointDTOForm) {

        // URL da imagem de capa padrão
        final String DEFAULT_IMAGE_URL = "/uploads/interestpointplaceholder.webp";

        // Verifica se o ponto de interesse já existe
        if (interestPointAlreadyExist(interestPointDTOForm.getName())) {
            throw new ServiceError("There is already a point of interest with that name: " + interestPointDTOForm.getName());
        } else {
            // Verifica se a imagem de capa está presente, senão usa a padrão
            if (interestPointDTOForm.getImageCoverUrl() == null || interestPointDTOForm.getImageCoverUrl().isEmpty()) {
                interestPointDTOForm.setImageCoverUrl(DEFAULT_IMAGE_URL);
            }

            // Mapeia e salva o ponto de interesse de acordo com o tipo
            switch (interestPointDTOForm.getInterestPointType()) {
                case "EVENT":
                    Event event = modelMapper.map(interestPointDTOForm, Event.class);
                    return interestPointRepository.save(event);
                case "HOTEL":
                    Hotel hotel = modelMapper.map(interestPointDTOForm, Hotel.class);
                    return interestPointRepository.save(hotel);
                case "EXPERIENCE":
                    Experience experience = modelMapper.map(interestPointDTOForm, Experience.class);
                    return interestPointRepository.save(experience);
                case "RESTAURANT":
                    Restaurant restaurant = modelMapper.map(interestPointDTOForm, Restaurant.class);
                    return interestPointRepository.save(restaurant);
                case "TOURIST_POINT":
                    TouristPoint touristPoint = modelMapper.map(interestPointDTOForm, TouristPoint.class);
                    return interestPointRepository.save(touristPoint);
                default:
                    throw new ServiceError("Tipo de interest point não encontrado: " + interestPointDTOForm.getInterestPointType());
            }
        }
    }


    public List<InterestPoint> getAll() {
        return interestPointRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public boolean interestPointAlreadyExist(String name){
        InterestPoint interestPointFound = interestPointRepository.getOnByName(name);
        return interestPointFound != null;
    }

    public InterestPoint getOne(Long id) {
        return interestPointRepository.findById(id).orElseThrow(
                () -> new ServiceError("Não foi possível encontrar um ponto de interesse com o ID: " + id)
        );
    }

    public InterestPoint update(Long id, InterestPointUpdateDTO interestPointDTO){
        InterestPoint interestPointFound = getOne(id);
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setRoad(interestPointDTO.getRoad());
        addressDTO.setNumber(interestPointDTO.getNumber());
        addressDTO.setZipCode(interestPointDTO.getZipCode());
        modelMapper.map(interestPointDTO, interestPointFound);
        modelMapper.map(addressDTO, interestPointFound.getAddress());
        try{
            return interestPointRepository.save(interestPointFound);
        }catch (Exception e){
            throw new ServiceError(e.getCause().getCause().getMessage());
        }
    }


    public InterestPoint getOneByName(String name) {
        InterestPoint interestPoint = interestPointRepository.getOnByName(name);
        if (interestPoint != null){
            return interestPointRepository.getOnByName(name);
        }else {
            throw new ServiceError("Could not found a Interest Point with this name: " + name);
        }

    }

    public List<InterestPoint> findAllByIds(List<Long> ids) {
        List<InterestPoint> interestPoints = new ArrayList<>();
        for (Long id : ids){
            interestPoints.add(getOne(id));
        }
        return interestPoints;
    }

    public String updateCoverImageUrl(Long id, String imageUrl) {
        InterestPoint interestPointFound = getOne(id);
        String imgUrl = interestPointFound.getImageCoverUrl();
        interestPointFound.setImageCoverUrl(imageUrl);
        interestPointRepository.save(interestPointFound);
        return imgUrl;
    }

    public List<InterestPointDTO> getAllByType(InterestPointType interestPointType) {
        List<InterestPoint> interestPoints = interestPointRepository.getAllByInterestPointType(interestPointType);
        return interestPoints.stream()
                .map(interestPoint -> modelMapper.map(interestPoint, InterestPointDTO.class))
                .collect(Collectors.toList());
    }




    public void saveMultipleImages(Long id, List<String> imagesUrl) {
        InterestPoint interestPoint = getOne(id);
        interestPoint.getImages().addAll(imagesUrl);
        interestPointRepository.save(interestPoint);
    }

    public void delete(Long id) {
        InterestPoint interestPoint = getOne(id);
        List<Itinerary> itineraries = itineraryRepository.findAll();
        List<Tourist> tourists = touristRepository.findAllByCommentsInterestPointId(interestPoint.getId());
        for (Itinerary itinerary : itineraries){
            itinerary.getInterestPoints().remove(interestPoint);
            itineraryRepository.save(itinerary);
        }
        for (Tourist tourist : tourists){
            tourist.getComments().removeIf(comment -> comment.getInterestPoint().getId().equals(interestPoint.getId()));
            touristRepository.save(tourist);
        }
        interestPointRepository.delete(interestPoint);
    }

}