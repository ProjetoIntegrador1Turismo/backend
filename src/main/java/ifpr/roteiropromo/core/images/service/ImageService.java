package ifpr.roteiropromo.core.images.service;


import ifpr.roteiropromo.core.errors.ServiceError;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Log4j2
public class ImageService {

    @Value("${upload.dir}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    public String saveImage(MultipartFile file) {
        try{
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());
            return "http://localhost:8081/uploads/" + fileName;
        }catch (IOException e){
            throw new ServiceError("An error occurred when trying to store image!");
        }
    }

    public void deleteImage(String imageUrl) {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        if (!"interestpointplaceholder.webp".equals(fileName)){
            try {
                log.info("Img que será deletada: " + fileName);
                Path filePath = Paths.get(uploadDir, fileName);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new ServiceError("An error occurred when trying to delete image!");
            }
        }
    }

}

