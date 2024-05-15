package ifpr.roteiropromo.core.admin.controller;

import ifpr.roteiropromo.core.admin.service.AdminService;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

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

    @PostMapping("/make-guide/{id}")
    public ResponseEntity<Guide> makeGuide(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Guide guide = adminService.makeGuide(id, body.get("cadasturCode"));
        return ResponseEntity.ok(guide);
    }
}
