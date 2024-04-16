package ifpr.roteiropromo.core.guideprofile.controller;


import ifpr.roteiropromo.core.guideprofile.domain.dtos.GuideProfileDTO;
import ifpr.roteiropromo.core.guideprofile.domain.dtos.GuideProfileDTOForm;
import ifpr.roteiropromo.core.guideprofile.domain.entities.GuideProfile;
import ifpr.roteiropromo.core.guideprofile.service.GuideProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guideprofile")
public class GuideProfileController {

    private final GuideProfileService guideProfileService;

    public GuideProfileController(GuideProfileService guideProfileService){
        this.guideProfileService = guideProfileService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<GuideProfileDTO>> getAll(){
        return ResponseEntity.ok(guideProfileService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GuideProfile> getOne(@PathVariable Long id){
        return ResponseEntity.ok(guideProfileService.findById(id));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GuideProfile> saveOne(@PathVariable Long id){
        return ResponseEntity.ok(guideProfileService.create(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteOne(@PathVariable Long id){
        guideProfileService.delete(id);
        return ResponseEntity.ok("Guide Profile removido com sucesso!");
    }

}
