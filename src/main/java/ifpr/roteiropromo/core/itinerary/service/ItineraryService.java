package ifpr.roteiropromo.core.itinerary.service;

import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTOForm;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.itinerary.repository.ItineraryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final ModelMapper modelMapper;
    private final ItineraryRepository itineraryRepository;
    private final InterestPointService interestPointService;

    public Itinerary create(ItineraryDTOForm itineraryDTOForm){
        Itinerary itinerary = modelMapper.map(itineraryDTOForm, Itinerary.class);
        return itineraryRepository.save(itinerary);
    }


    public List<Itinerary> findAll() {
        return itineraryRepository.findAll();
    }


    public Itinerary update(ItineraryDTO itineraryDTO, Long id){
        Itinerary itineraryFound = itineraryRepository.findById(id).orElseThrow(
                () -> new ServiceError("Não foi possível encontrar um roteiro com o ID: " + id)
        );
        modelMapper.map(itineraryDTO, itineraryFound);
        return itineraryRepository.save(itineraryFound);
    }


    public Itinerary findById(Long id){
        return itineraryRepository.findById(id).orElseThrow(
                () -> new ServiceError("Não foi possível encontrar um roteiro com o ID: " + id)
        );
    }

    @Transactional
    public Itinerary addInterestPoint(Long itineraryId, Long interestPointId) {
        Itinerary itinerary = findById(itineraryId);
        InterestPoint interestPointFound = interestPointService.findById(interestPointId);

        // Para adicionar o ponto de interesse na lista:
        List<InterestPoint> interestPoints = itinerary.getInterestPoints();
        interestPoints.add(interestPointFound);

        return itineraryRepository.save(itinerary);
    }



}
