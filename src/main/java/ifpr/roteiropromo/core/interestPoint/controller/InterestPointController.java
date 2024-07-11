package ifpr.roteiropromo.core.interestPoint.controller;

import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTO;
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

    //Cria um novo pont de interesse com os dados básicos - NOME e TIPO
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InterestPoint> createNewInterestPoint(
            @RequestBody InterestPointDTOForm interestPointDTOForm
    ){
        return ResponseEntity.ok(interestPointService.create(interestPointDTOForm));
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<InterestPoint>> getAllInterestPoints(){
        return ResponseEntity.ok(interestPointService.getAll());
    }

    @GetMapping("/name")
    public ResponseEntity<InterestPoint> getOneByName(@RequestParam String name){
        return ResponseEntity.ok(interestPointService.getOneByName(name));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<InterestPoint> getOneById(@PathVariable Long id){
        return ResponseEntity.ok(interestPointService.getOne(id));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InterestPoint> updateOneById(
            @PathVariable Long id,
            @RequestBody InterestPointDTO interestPointDTO
    ){
        return ResponseEntity.ok(interestPointService.update(id, interestPointDTO));
    }




}