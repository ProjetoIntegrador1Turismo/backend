package ifpr.roteiropromo.core.pagesource.service;

import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.pagesource.domain.HomePageDTO;
import ifpr.roteiropromo.core.pagesource.domain.InterestPointCardDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PageSourceService {

    private final InterestPointService interestPointService;
    private final ModelMapper modelMapper;

    public HomePageDTO getHomePageData(){
        HomePageDTO homePageDTO = new HomePageDTO();
        homePageDTO.setTop3InterestPoints(getTop3InterestPoints());
        return homePageDTO;
    }

    //Ajustar para capturar o ponto de interesse no arquivo de configuração
    private List<InterestPointCardDTO> getTop3InterestPoints() {
        List<InterestPointCardDTO> top3InterestPoints = new ArrayList<>();
//        InterestPoint cataratas = interestPointService.getOne(252L);
//        InterestPoint itapu = interestPointService.getOne(302L);
        InterestPoint parque = interestPointService.getOne(2L);
//        top3InterestPoints.add(modelMapper.map(cataratas, InterestPointCardDTO.class));
//        top3InterestPoints.add(modelMapper.map(itapu, InterestPointCardDTO.class));
        top3InterestPoints.add(modelMapper.map(parque, InterestPointCardDTO.class));
        return top3InterestPoints;
    }


}
