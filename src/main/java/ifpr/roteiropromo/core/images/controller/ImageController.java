package ifpr.roteiropromo.core.images.controller;


import ifpr.roteiropromo.core.errors.ServiceError;
import ifpr.roteiropromo.core.images.service.ImageService;
import ifpr.roteiropromo.core.interestPoint.service.InterestPointService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class ImageController {

    private final ImageService imageService;
    private final InterestPointService interestPointService;

    public ImageController(ImageService imageService, InterestPointService interestPointService) {
        this.imageService = imageService;
        this.interestPointService = interestPointService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file, @RequestParam Long id) {
        try {
            String imageUrl = imageService.saveImage(file);
            interestPointService.updateCoverImageUrl(id, imageUrl);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            throw new ServiceError("Could not upload image");
        }
    }





}
