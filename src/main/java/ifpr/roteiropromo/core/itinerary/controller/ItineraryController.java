package ifpr.roteiropromo.core.itinerary.controller;

import ifpr.roteiropromo.core.errors.StandartError;
import ifpr.roteiropromo.core.interestPoint.domain.entities.InterestPoint;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTOForm;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryResponseDTO;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryUpdateDTO;
import ifpr.roteiropromo.core.itinerary.service.ItineraryService;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Itinerary", description = "Operations related to Itineraries")
@RequestMapping("/itinerary")
public class ItineraryController {

    private final ItineraryService itineraryService;
    private final JwtTokenHandler jwtTokenHandler;

    public ItineraryController(ItineraryService itineraryService, JwtTokenHandler jwtTokenHandler){
        this.itineraryService = itineraryService;
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @PostMapping()
    @PreAuthorize("hasRole('GUIA')")
    @Operation(summary = "Create new itinerary",
            description = "Allow a Guide to create a new Itinerary and associate it with Interest Points",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itinerary created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItineraryDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Guide not found by email",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))})
    })
    public ResponseEntity<ItineraryDTO> saveOne(@RequestBody ItineraryDTOForm itineraryDTOForm){
        return ResponseEntity.ok(itineraryService.create(itineraryDTOForm));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GUIA')")
    @Operation(summary = "Update a itinerary",
            description = "Allow a Guide to update Itinerary data",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itinerary updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItineraryDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Guide not found by email",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "400", description = "Guide authenticated dont have a itinerary with id send in request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))})
    })
    public ResponseEntity<ItineraryDTO> update(@PathVariable Long id, @RequestBody ItineraryUpdateDTO itineraryDTO){
        return ResponseEntity.ok(itineraryService.update(id, itineraryDTO));
    }

    //Rota substituida pela de atualizar os dados do itinerario
//    @PutMapping("/{itineraryId}/add/{interestId}")
//    public ResponseEntity<ItineraryDTO> insertInterestPoint(@PathVariable Long itineraryId, @PathVariable Long interestId){
//        return ResponseEntity.ok(itineraryService.addInterestPoint(itineraryId, interestId));
//    }

    @GetMapping()
    @Operation(summary = "Get all Itineraries",
            description = "Return a list of all Itineraries available in the system",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itineraries found successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItineraryDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))})
    })
    public ResponseEntity<List<ItineraryDTO>> getAll(){
        return ResponseEntity.ok(itineraryService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one Itinerary",
            description = "Return a Itinerary by id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itineraries found successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItineraryDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Itinerary not found by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))})
    })
    public ResponseEntity<ItineraryDTO> getOne(@PathVariable Long id){
        return ResponseEntity.ok(itineraryService.findById(id));
    }

    @GetMapping("/guide-detail/{id}")
    @Operation(summary = "Get one Itinerary and Guide ownder",
            description = "Return a Itinerary and Guide owner by Itinerary id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itineraries found successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItineraryDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Itinerary not found by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))})
    })
    public ResponseEntity<ItineraryResponseDTO> getGuide(@PathVariable Long id){
        return ResponseEntity.ok(itineraryService.getGuideByItinerary(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GUIA')")
    @Operation(summary = "Delete one Itinerary",
            description = "Delete a from Itinerary from a Guide by id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itinerary deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Itinerary not found by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))})
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        itineraryService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/guide")
    @Operation(summary = "Get all Itineraries from Guide",
            description = "Return a list of all Itineraries from the Guide authenticated in the system",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itineraries found successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItineraryDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))})
    })
    public ResponseEntity<List<ItineraryDTO>> getByGuideEmail() {
        return ResponseEntity.ok(itineraryService.getByGuideEmail(jwtTokenHandler.getUserDataFromToken().getEmail()));
    }

}
