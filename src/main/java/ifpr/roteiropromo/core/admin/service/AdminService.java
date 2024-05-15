package ifpr.roteiropromo.core.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private InterestPointService interestPointService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private final File configFile = new File("src/main/resources/selectedInterestPoints.json");

    public List<InterestPoint> getSelectedInterestPointsDetails() throws IOException {
        List<Long> selectedInterestPointIds = readConfigFile().get("selectedInterestPoints");
        return interestPointService.findAllByIds(selectedInterestPointIds);
    }

    public void selectInterestPoints(List<Long> interestPointIds) throws IOException {
        Map<String, List<Long>> config = new HashMap<>();
        config.put("selectedInterestPoints", interestPointIds);
        objectMapper.writeValue(configFile, config);
    }

    public List<InterestPoint> getSelectedInterestPoints() throws IOException {
        List<Long> selectedInterestPointIds = readConfigFile().get("selectedInterestPoints");
        return interestPointService.findAllByIds(selectedInterestPointIds);
    }

    public void updateSelectedInterestPoint(int index, Long newInterestPointId) throws IOException {
        Map<String, List<Long>> config = readConfigFile();
        List<Long> selectedInterestPointIds = config.get("selectedInterestPoints");
        selectedInterestPointIds.set(index, newInterestPointId);
        objectMapper.writeValue(configFile, config);
    }

    public Guide makeGuide(Long id, String cadasturCode) {
        return userService.makeGuide(id, cadasturCode);
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
}
