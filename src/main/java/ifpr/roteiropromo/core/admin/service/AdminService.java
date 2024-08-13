package ifpr.roteiropromo.core.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.user.domain.dtos.GuideDTO;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.repository.UserRepository;
import ifpr.roteiropromo.core.user.service.UserService;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final JwtTokenHandler jwtTokenHandler;

    public List<GuideDTO> getAllUnapprovedGuides() {
        List<Guide> guides = userRepository.findAllUnapprovedGuides();
        return guides.stream()
                .map(guide -> modelMapper.map(guide, GuideDTO.class))
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



}
