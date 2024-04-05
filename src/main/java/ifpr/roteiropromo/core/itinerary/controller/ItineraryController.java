package ifpr.roteiropromo.core.itinerary.controller;

import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTOForm;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
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

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Itinerary>> getAll(){
        return ResponseEntity.ok(itineraryService.findAll());
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Itinerary> saveOne(@RequestBody ItineraryDTOForm itineraryForm){
        return ResponseEntity.ok(itineraryService.create(itineraryForm));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Itinerary> updateOne(@RequestBody ItineraryDTO itineraryDTO, @PathVariable Long id){

        Itinerary itineraryFound = itineraryService.findById(id);

        return ResponseEntity.ok(itineraryService.update(itineraryDTO, id));
    }

    // Nessa rota: Alterar depois para que o objeto InterestPoint venha do front e seja passado no corpo da requisição
    @PostMapping("/{itineraryId}/add/{interestPointId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Itinerary> addInterestPointToItinerary(@PathVariable Long itineraryId, @PathVariable Long interestPointId) {
        Itinerary updatedItinerary = itineraryService.addInterestPoint(itineraryId, interestPointId);

        return ResponseEntity.ok(updatedItinerary);
    }

}
