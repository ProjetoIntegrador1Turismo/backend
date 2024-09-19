package ifpr.roteiropromo.core.user.controller;
import ifpr.roteiropromo.core.errors.StandardError;
import ifpr.roteiropromo.core.itinerary.domain.dto.ItineraryDTO;
import ifpr.roteiropromo.core.pagesource.domain.InterestedTouristDTO;
import ifpr.roteiropromo.core.user.domain.dtos.GuideDTO;
import ifpr.roteiropromo.core.user.service.GuideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/guides")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Guide", description = "Operations dedicated to Guides")
public class GuideController {

    private final GuideService guideService;

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all Guides",
            description = "Allow to get a list of all registered guides.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guides found successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GuideDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) })
    })
    public ResponseEntity<List<GuideDTO>> getAllGuides(){
        return ResponseEntity.ok(guideService.getAllGuides());
    }

    @GetMapping("/itineraries")
    @PreAuthorize("hasRole('GUIA')")
    @Operation(summary = "Get Guide Itineraries",
            description = "Allow to get a list of all itineraries created by the authenticated guide.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itineraries found successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItineraryDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden access",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) })
    })
    public ResponseEntity<List<ItineraryDTO>> getItinerariesFromAuthenticatedGuide(){
        return ResponseEntity.ok(guideService.getItinerariesFromAuthenticatedGuide());
    }


    @GetMapping("/interestedTourists")
    @PreAuthorize("hasRole('GUIA')")
    public ResponseEntity<List<InterestedTouristDTO>> getInterestedTourists(){
        return ResponseEntity.ok(guideService.getInterestedTourists());
    }


}
