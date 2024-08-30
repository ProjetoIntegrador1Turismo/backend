package ifpr.roteiropromo.core.user.controller;

import ifpr.roteiropromo.core.user.domain.dtos.TouristDTO;
import ifpr.roteiropromo.core.user.domain.entities.Tourist;
import ifpr.roteiropromo.core.user.service.TouristService;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tourist")
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }


    @PostMapping("/signal/{itineraryId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TouristDTO> signalItineraryInterest(@PathVariable Long itineraryId){
        return ResponseEntity.ok(touristService.signalItineraryInterest(itineraryId));
    }

    @DeleteMapping("/signal/{itineraryId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> removeSignalInterested(@PathVariable Long itineraryId){
        touristService.removeInterestedItinerary(itineraryId);
        return ResponseEntity.noContent().build();
    }



}
