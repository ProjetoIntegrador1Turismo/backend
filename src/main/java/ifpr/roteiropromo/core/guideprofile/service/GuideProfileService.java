package ifpr.roteiropromo.core.guideprofile.service;

import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.guideprofile.domain.dtos.GuideProfileDTOForm;
import ifpr.roteiropromo.core.guideprofile.domain.entities.GuideProfile;
import ifpr.roteiropromo.core.guideprofile.repository.GuideProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuideProfileService {

    private final ModelMapper modelMapper;
    private final GuideProfileRepository guideProfileRepository;

    public GuideProfile create(GuideProfileDTOForm guideProfileDTOForm){
        GuideProfile guideProfile = modelMapper.map(guideProfileDTOForm, GuideProfile.class);
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

    public List<GuideProfile> findAll() {
        return guideProfileRepository.findAll();
    }
}
