package ifpr.roteiropromo.core.images.controller;


import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.errors.StandartError;
import ifpr.roteiropromo.core.images.service.ImageService;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.itinerary.service.ItineraryService;
import ifpr.roteiropromo.core.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Image", description = "Operations related to Images")
@RequestMapping("/file")
public class ImageController {

    private final ImageService imageService;
    private final InterestPointService interestPointService;
    private final UserService userService;
    private final ItineraryService itineraryService;

    public ImageController(ImageService imageService, InterestPointService interestPointService, UserService userService, ItineraryService itineraryService) {
        this.imageService = imageService;
        this.interestPointService = interestPointService;
        this.userService = userService;
        this.itineraryService = itineraryService;
    }

    @PostMapping("/upload/interest-point")
    @Operation(summary = "Upload Interest Point Image Cover",
            description = "Upload an image cover for a specific interest point.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Not exist by id provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))})
    })
    public ResponseEntity<String> uploadInterestPointImage(@RequestParam MultipartFile file, @RequestParam Long id) {
        try {
            String imageUrl = imageService.saveImage(file);
            interestPointService.updateCoverImageUrl(id, imageUrl);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            throw new ServiceError("Could not upload image");
        }
    }

    @PostMapping("/upload/interest-point/multiples")
    @Operation(summary = "Upload all Interest Point Images",
            description = "Upload images for a specific interest point.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Images saved"),
            @ApiResponse(responseCode = "400", description = "Not exist by id provided",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))})
    })
    public ResponseEntity<String> uploadInterestPointImages(
            @RequestParam (required = false) MultipartFile imgCover,
            @RequestParam (required = false) List<MultipartFile> files,
            @RequestParam Long id){

        if (imgCover == null && files == null){
            throw new ServiceError("At least one image needs to be sent!");
        }else {
            if(imgCover != null){
                String imageCover = imageService.saveImage(imgCover);
                String oldImgCover = interestPointService.updateCoverImageUrl(id, imageCover);
                imageService.deleteImage(oldImgCover);
            }
            if (files != null){
                List<String> imagesUrl = new ArrayList<>();
                for (MultipartFile file: files ) {
                    imagesUrl.add(imageService.saveImage(file));
                }
                interestPointService.saveMultipleImages(id, imagesUrl);
            }
            return ResponseEntity.ok("Images saved");
        }
    }

    @PostMapping("/upload/user")
    @Operation(summary = "Upload User Image Profile",
            description = "Upload an image profile for a specific user.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL image saved"),
            @ApiResponse(responseCode = "400", description = "Could not upload image",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))})
    })
    public ResponseEntity<String> uploadUserImage(@RequestParam MultipartFile file) {
        try {
            String imageUrl = imageService.saveImage(file);
            userService.updateProfileImageUrl(imageUrl);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            throw new ServiceError("Could not upload image");
        }
    }

    @PostMapping("/upload/itinerary")
    @Operation(summary = "Upload Itinerary Image Cover",
            description = "Upload an image cover for a specific itinerary.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL image saved"),
            @ApiResponse(responseCode = "400", description = "Could not upload image",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandartError.class))})
    })
    public ResponseEntity<String> uploadItineraryImage(@RequestParam MultipartFile file, @RequestParam Long id) {
        try {
            String imageUrl = imageService.saveImage(file);
            itineraryService.updateCoverImageUrl(id, imageUrl);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            throw new ServiceError("Could not upload image");
        }
    }

}


