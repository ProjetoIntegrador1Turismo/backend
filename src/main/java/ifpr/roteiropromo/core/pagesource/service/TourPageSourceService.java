package ifpr.roteiropromo.core.pagesource.service;


import ifpr.roteiropromo.core.comments.domain.DTO.CommentDTO;
import ifpr.roteiropromo.core.comments.service.CommentService;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTO;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.pagesource.domain.TopGuideDTO;
import ifpr.roteiropromo.core.pagesource.domain.TourPageDTO;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.service.GuideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourPageSourceService {

    private final InterestPointService interestPointService;
    private final GuideService guideService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;


    public TourPageDTO getTourPageData(Long id){
        TourPageDTO tourPageDTO = new TourPageDTO();
        tourPageDTO.setInterestPoint(this.getInterestPoint(id));
        tourPageDTO.setGuidesWhoOfferThisTour(this.getGuidesWhoOfferThisTour(id));
        tourPageDTO.setComments(this.getComments(id));
        return tourPageDTO;
    }


    private InterestPointDTO getInterestPoint(Long id){
        return modelMapper.map(interestPointService.getOne(id), InterestPointDTO.class);
    }


    private List<TopGuideDTO> getGuidesWhoOfferThisTour(Long id){
        List<Guide> guides = guideService.getGuidesWhoOfferTour(id);
        return guides.stream().map(guide -> modelMapper.map(guide, TopGuideDTO.class)).collect(Collectors.toList());
    }


    private List<CommentDTO> getComments(Long id){
        return commentService.getAllByInterestPoint(id);
    }

}
