package ifpr.roteiropromo.core.user.controller;

import ifpr.roteiropromo.core.errors.StandartError;
import ifpr.roteiropromo.core.user.domain.dtos.TouristDTO;
import ifpr.roteiropromo.core.user.service.TouristService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tourist")
@Tag(name = "Tourist", description = "Operations dedicated to Tourist")
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }


    @PostMapping("/signal/{itineraryId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Send interest",
            description = "Allow Tourist to signal interest in a specific itinerary.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Signal send successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TouristDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Itinerary not found for id informed.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) })
    })
    public ResponseEntity<TouristDTO> signalItineraryInterest(@PathVariable Long itineraryId){
        return ResponseEntity.ok(touristService.signalItineraryInterest(itineraryId));
    }

    @DeleteMapping("/signal/{itineraryId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Remove interest",
            description = "Allow Tourist to remove interest in a specific itinerary.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Signal removed successfully",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Tourist dont have interest in itinerary with id informed.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) })
    })
    public ResponseEntity<Void> removeSignalInterested(@PathVariable Long itineraryId){
        touristService.removeInterestedItinerary(itineraryId);
        return ResponseEntity.noContent().build();
    }



}
