package ifpr.roteiropromo.core.interestPoint.service;


import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTO;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.entities.*;
import ifpr.roteiropromo.core.interestPoint.repository.InterestPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class InterestPointService {

    private final ModelMapper modelMapper;
    private final InterestPointRepository interestPointRepository;


    public InterestPoint create(InterestPointDTOForm interestPointDTOForm) {

        // URL da imagem de capa padrão
        final String DEFAULT_IMAGE_URL = "http://localhost:8081/uploads/interestpointplaceholder.webp";

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

    private boolean interestPointAlreadyExist(String name){
        InterestPoint interestPointFound = interestPointRepository.getOnByName(name);
        return interestPointFound != null;
    }

    public InterestPoint getOne(Long id) {
        return interestPointRepository.findById(id).orElseThrow(
                () -> new ServiceError("Não foi possível encontrar um ponto de interesse com o ID: " + id)
        );
    }

    public InterestPoint update(Long id, InterestPointDTO interestPointDTO){
        InterestPoint interestPointFound = getOne(id);
        modelMapper.map(interestPointDTO, interestPointFound);
        try{
            return interestPointRepository.save(interestPointFound);
        }catch (Exception e){
            throw new ServiceError(e.getCause().getCause().getMessage());
        }
    }


    public InterestPoint getOneByName(String name) {
        return interestPointRepository.getOnByName(name);
    }

    //Duplicated Method -> USE getOne()
//    public InterestPoint findById(Long interestPointId) {
//        return interestPointRepository.findById(interestPointId).orElseThrow(
//                () -> new ServiceError("Não foi possível encontrar o ponto de interesse com o ID: " + interestPointId)
//        );
//    }

    public List<InterestPoint> findAllByIds(List<Long> ids) {
        List<InterestPoint> interestPoints = interestPointRepository.findAllById(ids);
        if (interestPoints.isEmpty()) {
            throw new ServiceError("Não foi possível encontrar pontos de interesse com os IDs fornecidos");
        }
        return interestPoints;
    }

    public void updateCoverImageUrl(Long id, String imageUrl) {
        InterestPoint interestPointFound = getOne(id);
        interestPointFound.setImageCoverUrl(imageUrl);
        interestPointRepository.save(interestPointFound);
    }

}