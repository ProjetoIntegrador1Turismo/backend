package ifpr.roteiropromo.core.interestPoint.service;

import ifpr.roteiropromo.core.erros.ServiceException;
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

        switch (interestPointDTOForm.getInterestPointType()) {
            case HOTEL:
                Hotel hotel = modelMapper.map(interestPointDTOForm, Hotel.class);
                return interestPointRepository.save(hotel);
            case EVENT:
                Event event = modelMapper.map(interestPointDTOForm, Event.class);
                return interestPointRepository.save(event);
            case TOURIST_POINT:
                TouristPoint touristPoint = modelMapper.map(interestPointDTOForm, TouristPoint.class);
                return interestPointRepository.save(touristPoint);
            case RESTAURANT:
                Restaurant restaurant = modelMapper.map(interestPointDTOForm, Restaurant.class);
                return interestPointRepository.save(restaurant);
            case EXPERIENCE:
                Experience experience = modelMapper.map(interestPointDTOForm, Experience.class);
                return interestPointRepository.save(experience);
            default:
                //Erro não esta passando a msg do erro. Falta corrigir
                throw new ServiceException("Interest Point Type not exist!");
        }

    }

    public List<InterestPoint> getAll() {
        return interestPointRepository.findAll();
    }


}
