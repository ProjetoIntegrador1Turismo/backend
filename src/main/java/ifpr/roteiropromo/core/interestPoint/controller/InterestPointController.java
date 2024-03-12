package ifpr.roteiropromo.core.interestPoint.controller;

import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTO;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
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
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InterestPoint> createNewInterestPoint(
            @RequestBody InterestPointDTOForm interestPointDTOForm
            ){
        log.info("Entrou no create com nova entidade: " + interestPointDTOForm.getName());
        return ResponseEntity.ok(interestPointService.create(interestPointDTOForm));
    }

    @GetMapping()
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<InterestPoint>> getAllInterestPoints(){
        return ResponseEntity.ok(interestPointService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterestPoint> getOneById(@PathVariable Long id){
        return ResponseEntity.ok(interestPointService.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterestPoint> updateOneById(
            @PathVariable Long id,
            @RequestBody InterestPointDTO interestPointDTO
            ){
        log.info("ENDEREÇO DO FRONT");
        log.info(interestPointDTO.getAddress().getRoad());
        return ResponseEntity.ok(interestPointService.update(id, interestPointDTO));
    }




}
