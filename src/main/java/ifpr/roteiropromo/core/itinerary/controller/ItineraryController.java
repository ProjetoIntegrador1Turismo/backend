package ifpr.roteiropromo.core.itinerary.controller;


import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTOForm;
import ifpr.roteiropromo.core.itinerary.domain.entity.Itinerary;
import ifpr.roteiropromo.core.itinerary.service.ItineraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itinerary")
@RequiredArgsConstructor
public class ItineraryController {

    private final ItineraryService itineraryService;

    @GetMapping()
    public ResponseEntity<String> getAll(){
        return ResponseEntity.ok("array with all selected tourist points");
    }

    @PostMapping()
    public ResponseEntity<Itinerary> saveOne(
            @RequestBody ItineraryDTOForm itineraryForm
            ){
        return ResponseEntity.ok(itineraryService.save(itineraryForm));
    }



}
