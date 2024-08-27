package ifpr.roteiropromo.core.admin.controller;

import ifpr.roteiropromo.core.admin.domain.FeaturedTouristPoint;
import ifpr.roteiropromo.core.admin.service.AdminService;
import ifpr.roteiropromo.core.user.domain.dtos.GuideDTO;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;


    @GetMapping("/unapproved-guides")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GuideDTO>> getAllUnapprovedGuides(){
        return ResponseEntity.ok(adminService.getAllUnapprovedGuides());
    }


    // Aprovar Guia
    @PutMapping("/approve-guide/{guideId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Guide> approveGuide(@PathVariable Long guideId) {
        Guide guide = adminService.approveGuide(guideId);
        return ResponseEntity.ok(guide);
    }


    @PutMapping("/disapprove-guide/{guideId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Guide> disapproveGuide(@PathVariable Long guideId) {
        Guide guide = adminService.disapproveGuide(guideId);
        return ResponseEntity.ok(guide);
    }

    @PostMapping("/principal-cards")
    public ResponseEntity<String> definePrincipalInterestPoints(@RequestBody List<Long> ids){
        return ResponseEntity.ok(adminService.setPrincipalInterestPoints(ids));
    }

    @GetMapping("/principal-cards")
    public ResponseEntity<List<FeaturedTouristPoint>> getFeaturedInterestpointsSelected(){
        return ResponseEntity.ok(adminService.getAllFeaturedPoints());
    }



}
