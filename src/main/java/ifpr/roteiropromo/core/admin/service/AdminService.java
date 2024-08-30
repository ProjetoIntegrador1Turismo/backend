package ifpr.roteiropromo.core.admin.service;

import ifpr.roteiropromo.core.admin.domain.FeaturedTouristPoint;
import ifpr.roteiropromo.core.admin.repository.FeaturedTouristPointRepository;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.user.domain.dtos.SimpleGuideDTO;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
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
public class AdminService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final JwtTokenHandler jwtTokenHandler;
    private final FeaturedTouristPointRepository featuredTouristPointRepository;
    private final InterestPointService interestPointService;

    public List<SimpleGuideDTO> getAllUnapprovedGuides() {
        List<Guide> guides = userRepository.findAllUnapprovedGuides();
        return guides.stream()
                .map(guide -> modelMapper.map(guide, SimpleGuideDTO.class))
                .collect(Collectors.toList());
    }

    public Guide approveGuide(Long id) {
        Guide guide = userService.findGuideById(id);

        if (guide.getIsApproved()) {
            throw new ServiceError("Guide já está aprovado!");
        }

        // Adicionando role GUIDE no keycloak:
        try {
            String userId = userService.getUserIdFromKeycloak(guide);
            userService.addRoleToUser(userId, jwtTokenHandler.getAdminToken(), "GUIA");
        } catch (Exception e) {
            throw new ServiceError("Failed to assign role to user: " + e.getMessage());
        }

        guide.setIsApproved(true);
        userRepository.save(guide);

        return guide;
    }

    public Guide disapproveGuide(Long guideId) {
        Guide guide = userService.findGuideById(guideId);
        if (!guide.getIsApproved()){
            throw new ServiceError("Guide já está desativado!");
        }
        guide.setIsApproved(false);
        return userRepository.save(guide);
    }


    public String setPrincipalInterestPoints(List<Long> ids) {
        validateMaxSize(ids);
        List<InterestPoint> interestPoints = interestPointService.findAllByIds(ids);
        removeAllFeaturedInterestPointsDefined();
        List<FeaturedTouristPoint> createdFeaturedPoints = createNewFeaturedTouristPoints(interestPoints);
        featuredTouristPointRepository.saveAll(createdFeaturedPoints);
        return "Principal InterestPoint List updated!";
    }

    private void validateMaxSize(List<Long> ids) {
        if (ids.size() != 3){
            throw new ServiceError("It is mandatory that the list contains 3 ids and not " + ids.size());
        }
    }

    private List<FeaturedTouristPoint> createNewFeaturedTouristPoints(List<InterestPoint> interestPoints) {
        List<FeaturedTouristPoint> featuredTouristPoints = new ArrayList<>();
        for (InterestPoint interestPoint : interestPoints){
            FeaturedTouristPoint featuredTouristPoint = new FeaturedTouristPoint();
            featuredTouristPoint.setInterestPoint(interestPoint);
            featuredTouristPoints.add(featuredTouristPoint);
        }
        return featuredTouristPoints;
    }

    private void removeAllFeaturedInterestPointsDefined() {
        featuredTouristPointRepository.deleteAll();
    }

    public List<FeaturedTouristPoint> getAllFeaturedPoints() {
        return featuredTouristPointRepository.findAll();
    }
}
