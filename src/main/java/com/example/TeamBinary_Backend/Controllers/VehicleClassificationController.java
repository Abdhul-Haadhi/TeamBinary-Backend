package com.example.TeamBinary_Backend.Controllers;

import com.example.TeamBinary_Backend.DTOs.VehicleClassificationDTO;
import com.example.TeamBinary_Backend.Services.VehicleClassificationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleClassificationController {

    private final VehicleClassificationService service;

    public VehicleClassificationController(VehicleClassificationService service) {
        this.service = service;
    }

    @PostMapping(value = "/classify")
    public VehicleClassificationDTO classify(@RequestParam("image") MultipartFile image)

            throws IOException {

        return service.classifyVehicle(image);
    }

    @GetMapping("/all")
    public List<VehicleClassificationDTO> getAll() {

        return service.getAll();
    }
}
