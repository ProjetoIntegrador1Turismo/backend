package ifpr.roteiropromo.core.interestPoint.controller;

import ifpr.roteiropromo.core.images.service.ImageService;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointUpdateDTO;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/interestpoint")
@Log4j2
public class InterestPointController {

    private final InterestPointService interestPointService;
    private final ImageService imageService;


    public InterestPointController(InterestPointService interestPointService, ImageService imageService, ImageService imageService1){
        this.interestPointService = interestPointService;
        this.imageService = imageService1;
    }

    //Cria um novo ponto de interesse com todos os dados
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InterestPoint> createNewInterestPoint(@RequestBody InterestPointDTOForm interestPointDTOForm){
        return ResponseEntity.ok(interestPointService.create(interestPointDTOForm));
    }

    @PostMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public void createNewInterestPointTeste(@RequestParam("dados") String dadosJson, @RequestParam MultipartFile file) throws IOException {
        //return ResponseEntity.ok(interestPointService.create(dados));
        String imageUrl = imageService.saveImage(file);
        log.info( "LINK DA IMAGEM SALVA" + imageUrl);
        log.info(dadosJson);
    }



    @GetMapping()
    //@PreAuthorize("hasRole('USER')")
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
            @RequestBody InterestPointUpdateDTO interestPointDTO
    ){
        return ResponseEntity.ok(interestPointService.update(id, interestPointDTO));
    }




}