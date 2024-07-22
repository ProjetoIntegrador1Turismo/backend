package ifpr.roteiropromo.core.admin.controller;

import ifpr.roteiropromo.core.admin.service.AdminService;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    @GetMapping("/selected-interest-points-details")
    public ResponseEntity<List<InterestPoint>> getSelectedInterestPointsDetails() throws IOException {
        List<InterestPoint> selectedInterestPoints = adminService.getSelectedInterestPointsDetails();
        return ResponseEntity.ok(selectedInterestPoints);
    }

    @PostMapping("/select-interest-points")
    public ResponseEntity<Void> selectInterestPoints(@RequestBody List<Long> interestPointIds) throws IOException {
        adminService.selectInterestPoints(interestPointIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/selected-interest-points")
    public ResponseEntity<List<InterestPoint>> getSelectedInterestPoints() throws IOException {
        List<InterestPoint> selectedInterestPoints = adminService.getSelectedInterestPoints();
        return ResponseEntity.ok(selectedInterestPoints);
    }

    @PutMapping("/selected-interest-points/{index}/{newInterestPointId}")
    public ResponseEntity<Void> updateSelectedInterestPoint(@PathVariable int index, @PathVariable Long newInterestPointId) throws IOException {
        adminService.updateSelectedInterestPoint(index, newInterestPointId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/unapproved-guides")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Guide>> getAllUnapprovedGuides(){
        return ResponseEntity.ok(userService.getAllUnapprovedGuides());
    }


    // Aprovar Guia
    @PutMapping("/approve-guide/{guideId}")
    public ResponseEntity<Guide> approveGuide(@PathVariable Long guideId) {
        Guide guide = userService.approveGuide(guideId);
        return ResponseEntity.ok(guide);
    }


    @PutMapping("/disapprove-guide/{guideId}")
    public ResponseEntity<Guide> disapproveGuide(@PathVariable Long guideId) {
        Guide guide = userService.disapproveGuide(guideId);
        return ResponseEntity.ok(guide);
    }


}
