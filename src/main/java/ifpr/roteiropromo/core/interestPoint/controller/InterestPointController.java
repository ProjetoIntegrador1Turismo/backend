package ifpr.roteiropromo.core.interestPoint.controller;

import ifpr.roteiropromo.core.errors.StandartError;
import ifpr.roteiropromo.core.images.service.ImageService;
import ifpr.roteiropromo.core.enums.InterestPointType;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTO;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointDTOForm;
import ifpr.roteiropromo.core.interestPoint.domain.dtos.InterestPointUpdateDTO;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.security.CustomAccessDeniedHandler;
import ifpr.roteiropromo.core.security.CustomAuthenticationEntryPoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Interest Point", description = "Operations related to Interest Points")
public class InterestPointController {

    private final InterestPointService interestPointService;
    private final ImageService imageService;


    public InterestPointController(InterestPointService interestPointService, ImageService imageService){
        this.interestPointService = interestPointService;
        this.imageService = imageService;
    }

    //Cria um novo ponto de interesse com todos os dados
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new Interest Point",
            description = "Allow ADMIN user to create new point of interest, according to the available types: HOTEL, EXPERIENCE, EVENT, RESTAURANT and TOURIST_POINT",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<InterestPoint> createNewInterestPoint(@RequestBody InterestPointDTOForm interestPointDTOForm){
        return ResponseEntity.ok(interestPointService.create(interestPointDTOForm));
    }

    @GetMapping()
    @Operation(summary = "Get all Interest Points", description = "Retrieve all available points of interest, even if they are of different types.")
    public ResponseEntity<List<InterestPoint>> getAllInterestPoints(){
        return ResponseEntity.ok(interestPointService.getAll());
    }

    @GetMapping("/name")
    @Operation(summary = "Get a Interest Point by name",
            description = "Allow ADMIN user to retrieve one Interest Point by name.",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<InterestPoint> getOneByName(@RequestParam String name){
        return ResponseEntity.ok(interestPointService.getOneByName(name));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Interest Point by id",
            description = "Allow ADMIN user to retrieve one Interest Point by id.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interest Point found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InterestPoint.class)) }),
            @ApiResponse(responseCode = "400", description = "Not exist by id provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))})
    })
    public ResponseEntity<InterestPoint> getOneById(@PathVariable Long id){
        return ResponseEntity.ok(interestPointService.getOne(id));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a Interest Point",
            description = "Allow ADMIN user to update Interest Point data by id.",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<InterestPoint> updateOneById(
            @PathVariable Long id,
            @RequestBody InterestPointUpdateDTO interestPointDTO
    ){
        return ResponseEntity.ok(interestPointService.update(id, interestPointDTO));
    }

    @GetMapping("/type")
    @Operation(summary = "Get all interest point by type",
            description = "Return all interest points filtered by type : HOTEL, EXPERIENCE, EVENT, RESTAURANT or TOURIST_POINT")
    public ResponseEntity<List<InterestPointDTO>> getAllByType(@RequestParam String type) {
        InterestPointType interestPointType;
        try {
            interestPointType = InterestPointType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(interestPointService.getAllByType(interestPointType));
    }





}