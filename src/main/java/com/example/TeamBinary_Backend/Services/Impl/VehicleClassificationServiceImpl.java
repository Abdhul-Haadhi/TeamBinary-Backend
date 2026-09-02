package com.example.TeamBinary_Backend.Services.Impl;

import com.example.TeamBinary_Backend.DTOs.VehicleClassificationDTO;
import com.example.TeamBinary_Backend.Entities.VehicleClassificationEntity;
import com.example.TeamBinary_Backend.Repositories.VehicleClassificationRepository;
import com.example.TeamBinary_Backend.Services.VehicleClassificationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class VehicleClassificationServiceImpl implements VehicleClassificationService {

    private final VehicleClassificationRepository repository;

    public VehicleClassificationServiceImpl(VehicleClassificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public VehicleClassificationDTO classifyVehicle(MultipartFile image)
            throws IOException {

        String[] vehicles = {

                "Car",
                "Bus",
                "Van",
                "Lorry",
                "Motorcycle",
                "Three Wheeler"
        };

        Random random = new Random();

        String vehicle =
                vehicles[random.nextInt(vehicles.length)];

        String confidence =
                (90 + random.nextInt(10)) + "%";

        VehicleClassificationEntity entity = new VehicleClassificationEntity();

        entity.setVehicleType(vehicle);
        entity.setConfidence(confidence);
        entity.setCamera("Camera 01");
        entity.setDetectedTime(LocalTime.now().toString());
        entity.setImagePath(image.getOriginalFilename());

        repository.save(entity);

        VehicleClassificationDTO dto =
                new VehicleClassificationDTO();

        dto.setId(entity.getId());
        dto.setVehicleType(entity.getVehicleType());
        dto.setConfidence(entity.getConfidence());
        dto.setCamera(entity.getCamera());
        dto.setDetectedTime(entity.getDetectedTime());
        dto.setImagePath(entity.getImagePath());

        return dto;
    }

    @Override
    public List<VehicleClassificationDTO> getAll() {

        return repository.findAll()

                .stream()

                .map(v -> {

                    VehicleClassificationDTO dto =
                            new VehicleClassificationDTO();

                    dto.setId(v.getId());
                    dto.setVehicleType(v.getVehicleType());
                    dto.setConfidence(v.getConfidence());
                    dto.setCamera(v.getCamera());
                    dto.setDetectedTime(v.getDetectedTime());
                    dto.setImagePath(v.getImagePath());

                    return dto;

                }).collect(Collectors.toList());
    }
}
