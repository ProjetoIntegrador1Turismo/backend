package ifpr.roteiropromo.core.interestPoint.service;


import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTO;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.entities.*;
import ifpr.roteiropromo.core.interestPoint.repository.InterestPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class InterestPointService {

    private final ModelMapper modelMapper;
    private final InterestPointRepository interestPointRepository;
    public InterestPoint create(InterestPointDTOForm interestPointDTOForm) {
        log.info("Acessou o metodo com switch");

        if(interestPointAlreadyExist(interestPointDTOForm.getName())){
            throw new ServiceError("There is already a point of interest with that name: " + interestPointDTOForm.getName());
        }else{
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
        return interestPointRepository.findAll();
    }

    private boolean interestPointAlreadyExist(String name){
        InterestPoint interestPointFound = interestPointRepository.getOnByName(name);
        return interestPointFound != null;
    }

    public InterestPoint getOne(Long id) {
        return interestPointRepository.findById(id).orElseThrow(
                () -> new ServiceError("Não foi possível encontrar o ponto de interesse com o ID: " + id)
        );
    }

    public InterestPoint update(Long id, InterestPointDTO interestPointDTO) {
        InterestPoint interestPointFound = interestPointRepository.findById(id).orElseThrow(
                () -> new ServiceError("Não foi possível encontrar o ponto de interesse com o ID: " + id)
        );
        modelMapper.map(interestPointDTO, interestPointFound);
        return interestPointRepository.save(interestPointFound);
    }


    public InterestPoint getOneByName(String name) {
        return interestPointRepository.getOnByName(name);
    }

    public InterestPoint findById(Long interestPointId) {
        return interestPointRepository.findById(interestPointId).orElseThrow(
                () -> new ServiceError("Não foi possível encontrar o ponto de interesse com o ID: " + interestPointId)
        );
    }
}