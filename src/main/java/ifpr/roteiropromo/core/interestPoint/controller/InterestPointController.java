package ifpr.roteiropromo.core.interestPoint.controller;

import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interestpoint")
@Log4j2
public class InterestPointController {

    private final InterestPointService interestPointService;

    public InterestPointController(InterestPointService interestPointService){
        this.interestPointService = interestPointService;
    }

    //Save a basic template for all Interest Point types

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InterestPoint> createNewInterestPoint(
            @RequestBody InterestPointDTOForm interestPointDTOForm
            ){
        log.info("Entrou no create com nova entidade: " + interestPointDTOForm.getName());
        return ResponseEntity.ok(interestPointService.create(interestPointDTOForm));
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<InterestPoint>> getAllInterestPoints(){
        return ResponseEntity.ok(interestPointService.getAll());
    }




}
