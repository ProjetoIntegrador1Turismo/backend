package ifpr.roteiropromo.core.admin.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import ifpr.roteiropromo.core.admin.service.AdminService;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final InterestPointService interestPointService;
    private final ObjectMapper objectMapper;
    private final File configFile;

    public AdminController(AdminService adminService, InterestPointService interestPointService, ObjectMapper objectMapper) {
        this.adminService = adminService;
        this.interestPointService = interestPointService;
        this.objectMapper = objectMapper;
        this.configFile = new File("src/main/resources/selectedInterestPoints.json");
    }

    private Map<String, List<Long>> readConfigFile() throws IOException {
        if (configFile.length() == 0) {
            return new HashMap<String, List<Long>>() {{
                put("selectedInterestPoints", new ArrayList<>());
            }};
        } else {
            return objectMapper.readValue(configFile, Map.class);
        }
    }

    @GetMapping("/selected-interest-points-details")
    public ResponseEntity<List<InterestPoint>> getSelectedInterestPointsDetails() throws IOException {
        Map<String, List<Long>> config = readConfigFile();
        List<Long> selectedInterestPointIds = config.get("selectedInterestPoints");
        List<InterestPoint> selectedInterestPoints = interestPointService.findAllByIds(selectedInterestPointIds);
        return ResponseEntity.ok(selectedInterestPoints);
    }

    @PostMapping("/select-interest-points")
    public ResponseEntity<Void> selectInterestPoints(@RequestBody List<Long> interestPointIds) throws IOException {
        Map<String, List<Long>> config = readConfigFile();
        config.put("selectedInterestPoints", interestPointIds);
        objectMapper.writeValue(configFile, config);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/selected-interest-points")
    public ResponseEntity<List<InterestPoint>> getSelectedInterestPoints() throws IOException {
        Map<String, List<Long>> config = readConfigFile();
        List<Long> selectedInterestPointIds = config.get("selectedInterestPoints");
        List<InterestPoint> selectedInterestPoints = interestPointService.findAllByIds(selectedInterestPointIds);
        return ResponseEntity.ok(selectedInterestPoints);
    }

    // Substitur o X pelo Y
    @PutMapping("/selected-interest-points/{index}/{newInterestPointId}")
    public ResponseEntity<Void> updateSelectedInterestPoint(@PathVariable int index, @PathVariable Long newInterestPointId) throws IOException {

        InterestPoint interestPoint = interestPointService.findById(newInterestPointId);
        if (interestPoint == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, List<Long>> config = readConfigFile();
        List<Long> selectedInterestPointIds = config.get("selectedInterestPoints");
        selectedInterestPointIds.set(index, newInterestPointId);
        objectMapper.writeValue(configFile, config);
        return ResponseEntity.ok().build();
    }
}