package ifpr.roteiropromo.core.interestPoint.controller;

import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interestpoint")
public class InterestPointController {

    private final InterestPointService interestPointService;

    public InterestPointController(InterestPointService interestPointService){
        this.interestPointService = interestPointService;
    }

    //Save a basic template for all Interest Point types
    @PostMapping
    public ResponseEntity<InterestPoint> createNewInterestPoint(
            @RequestBody InterestPointDTOForm interestPointDTOForm
            ){
        return ResponseEntity.ok(interestPointService.create(interestPointDTOForm));
    }

    @GetMapping
    public ResponseEntity<List<InterestPoint>> getAllInterestPoints(){
        return ResponseEntity.ok(interestPointService.getAll());
    }




}
