package ifpr.roteiropromo.core.images.controller;


import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.images.service.ImageService;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import ifpr.roteiropromo.core.itinerary.service.ItineraryService;
import ifpr.roteiropromo.core.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
public class ImageController {

    private final ImageService imageService;
    private final InterestPointService interestPointService;
    private final UserService userService;
    private final ItineraryService   itineraryService;

    public ImageController(ImageService imageService, InterestPointService interestPointService, UserService userService, ItineraryService itineraryService) {
        this.imageService = imageService;
        this.interestPointService = interestPointService;
        this.userService = userService;
        this.itineraryService = itineraryService;
    }

    @PostMapping("/upload/interest-point")
    public ResponseEntity<String> uploadInterestPointImage(@RequestParam MultipartFile file, @RequestParam Long id) {
        try {
            //Tratar erro quando uma imagem nao é enviada!
            String imageUrl = imageService.saveImage(file);
            interestPointService.updateCoverImageUrl(id, imageUrl);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            throw new ServiceError("Could not upload image");
        }
    }

    @PostMapping("/upload/interest-point/multiples")
    public ResponseEntity<String> uploadInterestPointImages(@RequestParam List<MultipartFile> files, @RequestParam Long id) {
        try {
            List<String> imagesUrl = new ArrayList<>();
            for (MultipartFile file: files ) {
                imagesUrl.add(imageService.saveImage(file));
            }
            interestPointService.saveMultipleImages(id, imagesUrl);
            return ResponseEntity.ok("Images saved");
        } catch (Exception e) {
            throw new ServiceError("Could not upload image");
        }
    }

    @PostMapping("/upload/user")
    public ResponseEntity<String> uploadUserImage(@RequestParam MultipartFile file, @RequestParam Long id) {
        try {
            String imageUrl = imageService.saveImage(file);
            userService.updateProfileImageUrl(id, imageUrl);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            throw new ServiceError("Could not upload image");
        }
    }

    @PostMapping("/upload/itinerary")
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


