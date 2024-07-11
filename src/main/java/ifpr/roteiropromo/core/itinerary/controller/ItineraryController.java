package ifpr.roteiropromo.core.itinerary.controller;

import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTOForm;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryResponseDTO;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryUpdateDTO;
import ifpr.roteiropromo.core.itinerary.service.ItineraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController {

    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService){
        this.itineraryService = itineraryService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('GUIA')")
    public ResponseEntity<ItineraryDTO> saveOne(@RequestBody ItineraryDTOForm itineraryDTOForm){
        return ResponseEntity.ok(itineraryService.create(itineraryDTOForm));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('GUIA')")
    public ResponseEntity<ItineraryDTO> update(@PathVariable Long id, @RequestBody ItineraryUpdateDTO itineraryDTO){
        return ResponseEntity.ok(itineraryService.update(id, itineraryDTO));
    }

    @PutMapping("/{itineraryId}/add/{interestId}")
    public ResponseEntity<ItineraryDTO> insertInterestPoint(@PathVariable Long itineraryId, @PathVariable Long interestId){
        return ResponseEntity.ok(itineraryService.addInterestPoint(itineraryId, interestId));
    }

    @GetMapping()
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ItineraryDTO>> getAll(){
        return ResponseEntity.ok(itineraryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItineraryDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(itineraryService.findById(id));
    }

    @GetMapping("/guide-detail/{id}")
    public ResponseEntity<ItineraryResponseDTO> getGuide(@PathVariable Long id){
        return ResponseEntity.ok(itineraryService.getGuideByItinerary(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        itineraryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
