package ifpr.roteiropromo.core.user.controller;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.pagesource.domain.TopGuideDTO;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.service.GuideService;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/guides")
@Log4j2
@RequiredArgsConstructor
public class GuideController {

    private final GuideService guideService;

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Guide>> getAllGuides(){
        return ResponseEntity.ok(guideService.getAllGuides());
    }

    @GetMapping("/itineraries")
    @PreAuthorize("hasRole('GUIA')")
    public ResponseEntity<List<Itinerary>> getItinerariesFromAuthenticatedGuide(){
        return ResponseEntity.ok(guideService.getItinerariesFromAuthenticatedGuide());
    }


}
