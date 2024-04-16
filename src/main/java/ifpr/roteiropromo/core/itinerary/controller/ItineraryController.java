package ifpr.roteiropromo.core.itinerary.controller;

import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.guideprofile.domain.entities.GuideProfile;
import ifpr.roteiropromo.core.guideprofile.service.GuideProfileService;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTOForm;
import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.itinerary.service.ItineraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController {

    private final ItineraryService itineraryService;
    private final GuideProfileService guideProfileService;

    public ItineraryController(ItineraryService itineraryService, GuideProfileService guideProfileService){
        this.itineraryService = itineraryService;
        this.guideProfileService = guideProfileService;
    }



//    @GetMapping()
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<List<Itinerary>> getAll(){
//        return ResponseEntity.ok(itineraryService.findAll());
//    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ItineraryDTO>> getAll(){
        return ResponseEntity.ok(itineraryService.findAll());
    }

    // save antigo
//    @PostMapping()
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Itinerary> saveOne(@RequestBody ItineraryDTOForm itineraryForm){
//
//        return ResponseEntity.ok(itineraryService.create(itineraryForm));
//    }

    @PostMapping()
    @PreAuthorize("hasRole('GUIA')")
    public ResponseEntity<Itinerary> saveOne(@RequestBody ItineraryDTOForm itineraryDTOForm){
        if (itineraryDTOForm.getGuideProfileId() == null) {
            throw new ServiceError("Perfil guia não informado!");
        }

        return ResponseEntity.ok(itineraryService.create(itineraryDTOForm));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Itinerary> updateOne(@RequestBody ItineraryDTO itineraryDTO, @PathVariable Long id){

        return ResponseEntity.ok(itineraryService.update(itineraryDTO, id));
    }

    // Nessa rota: Alterar depois para que o objeto InterestPoint venha do front e seja passado no corpo da requisição (??)
    @PostMapping("/{itineraryId}/add/{interestPointId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Itinerary> addInterestPointToItinerary(@PathVariable Long itineraryId, @PathVariable Long interestPointId) {
        Itinerary updatedItinerary = itineraryService.addInterestPoint(itineraryId, interestPointId);
        return ResponseEntity.ok(updatedItinerary);
    }

}
