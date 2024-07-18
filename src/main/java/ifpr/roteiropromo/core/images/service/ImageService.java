package ifpr.roteiropromo.core.images.service;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
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

    public String saveImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, file.getBytes());
        return "http://localhost:8081/uploads/" + fileName;
    }

}
