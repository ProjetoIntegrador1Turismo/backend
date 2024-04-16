package ifpr.roteiropromo.core.guideprofile.service;

import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.guideprofile.domain.dtos.GuideProfileDTO;
import ifpr.roteiropromo.core.guideprofile.domain.dtos.GuideProfileDTOForm;
import ifpr.roteiropromo.core.guideprofile.domain.entities.GuideProfile;
import ifpr.roteiropromo.core.guideprofile.repository.GuideProfileRepository;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuideProfileService {

    private final ModelMapper modelMapper;
    private final GuideProfileRepository guideProfileRepository;
    private final UserService userService;

    public GuideProfile create(GuideProfileDTOForm guideProfileDTOForm){
        User user = userService.findById(guideProfileDTOForm.getUser().getId());

        if (user == null) {
            throw new ServiceError("User with id " + guideProfileDTOForm.getUser().getId() + " not found");
        }

        GuideProfile guideProfile = modelMapper.map(guideProfileDTOForm, GuideProfile.class);
        guideProfile.setGuide((Guide) user);
        return guideProfileRepository.save(guideProfile);
    }

    public GuideProfile update(GuideProfileDTOForm guideProfileDTOForm, Long id){
        GuideProfile guideProfileFound = guideProfileRepository.findById(id).orElseThrow(
                () -> new ServiceError("Não foi possível encontrar um perfil de guia com o ID: " + id)
        );
        modelMapper.map(guideProfileDTOForm, guideProfileFound);
        return guideProfileRepository.save(guideProfileFound);
    }

    public GuideProfile findById(Long id){
        return guideProfileRepository.findById(id).orElseThrow(
                () -> new ServiceError("Não foi possível encontrar um perfil de guia com o ID: " + id)
        );
    }

    public GuideProfile delete(Long id){
        GuideProfile guideProfile = findById(id);
        guideProfileRepository.delete(guideProfile);
        return guideProfile;
    }


    //    public List<GuideProfile> findAll() {
    //        return guideProfileRepository.findAll();
    //    }

    public List<GuideProfileDTO> findAll() {
        List<GuideProfile> guideProfiles = guideProfileRepository.findAll();
        return guideProfiles.stream()
                .map(guideProfile -> modelMapper.map(guideProfile, GuideProfileDTO.class))
                .collect(Collectors.toList());
    }
}
