package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VehicleService {

    private final Path uploadDirectory =
            Paths.get("uploads");

    public String saveVehicleImage(MultipartFile file)
            throws IOException {

        // Create uploads folder if it doesn't exist
        Files.createDirectories(uploadDirectory);

        // Original filename
        String fileName = file.getOriginalFilename();

        if (fileName == null || fileName.isBlank()) {
            fileName = "vehicle-image.jpg";
        }

        // Save image
        Path filePath =
                uploadDirectory.resolve(fileName);

        Files.write(
                filePath,
                file.getBytes()
        );

        return filePath.toString();
    }
}