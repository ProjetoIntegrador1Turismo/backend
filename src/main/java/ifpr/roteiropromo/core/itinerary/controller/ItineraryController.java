package ifpr.roteiropromo.core.itinerary.controller;


import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTOForm;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.itinerary.service.ItineraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itinerary")
@RequiredArgsConstructor
public class ItineraryController {

    private final ItineraryService itineraryService;

    @GetMapping()
    public ResponseEntity<List<Itinerary>> getAll(){
        return ResponseEntity.ok(itineraryService.findAll());
    }

    @PostMapping()
    public ResponseEntity<Itinerary> saveOne(@RequestBody ItineraryDTOForm itineraryForm){
        return ResponseEntity.ok(itineraryService.create(itineraryForm));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Itinerary> updateOne(@RequestBody ItineraryDTO itinerary, @PathVariable Long id){
        return ResponseEntity.ok(itineraryService.update(itinerary, id));
    }

}
