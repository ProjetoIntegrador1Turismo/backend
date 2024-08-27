package ifpr.roteiropromo.core.admin.controller;

import ifpr.roteiropromo.core.admin.domain.FeaturedTouristPoint;
import ifpr.roteiropromo.core.admin.service.AdminService;
import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.user.domain.dtos.GuideDTO;
import ifpr.roteiropromo.core.user.domain.dtos.SimpleGuideDTO;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.domain.entities.User;
import ifpr.roteiropromo.core.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "Admin Controller for managing guides and featured interest points.")
public class AdminController {

    private final AdminService adminService;
    private final ModelMapper mapper;


    @GetMapping("/unapproved-guides")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all unapproved guides", description = "Retrieve all guides that are not approved yet.")
    @ApiResponse(responseCode = "200",
            description = "List of unapproved guides",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleGuideDTO.class))})
    public ResponseEntity<List<SimpleGuideDTO>> getAllUnapprovedGuides(){
        return ResponseEntity.ok(adminService.getAllUnapprovedGuides());
    }


    @PutMapping("/approve-guide/{guideId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Approve guide", description = "Approve a guide that is not approved yet.")
    @ApiResponse(responseCode = "200",
            description = "Guide approved successfully",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleGuideDTO.class))})
    public ResponseEntity<SimpleGuideDTO> approveGuide(@PathVariable Long guideId) {
        Guide guide = adminService.approveGuide(guideId);
        return ResponseEntity.ok(mapper.map(guide, SimpleGuideDTO.class));
    }


    @PutMapping("/disapprove-guide/{guideId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Disapprove guide", description = "Disapprove a guide that is approved.")
    @ApiResponse(responseCode = "200",
            description = "Guide disapproved successfully",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleGuideDTO.class))})
    public ResponseEntity<SimpleGuideDTO> disapproveGuide(@PathVariable Long guideId) {
        Guide guide = adminService.disapproveGuide(guideId);
        return ResponseEntity.ok(mapper.map(guide, SimpleGuideDTO.class));
    }

    @PostMapping("/principal-cards")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Define principal tourist point", description = "Define the principals tourist points that will be displayed in the 3 cards on home screen.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Principal tourist points defined successfully"),
            @ApiResponse(responseCode = "400", description = "Mandatory list containing 3 ids of tourist points.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ServiceError.class))})
    })
    public ResponseEntity<String> definePrincipalInterestPoints(@RequestBody List<Long> ids){
        return ResponseEntity.ok(adminService.setPrincipalInterestPoints(ids));
    }

    @GetMapping("/principal-cards")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get principal tourist points", description = "Retrieve the actual featured tourist points.")
    @ApiResponse(responseCode = "200", description = "List of featured tourist points",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = FeaturedTouristPoint.class))))
    public ResponseEntity<List<FeaturedTouristPoint>> getFeaturedInterestpointsSelected(){
        return ResponseEntity.ok(adminService.getAllFeaturedPoints());
    }



}
