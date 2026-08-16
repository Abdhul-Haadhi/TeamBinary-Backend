package com.example.demo.controller;

import com.example.demo.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "http://localhost:5173")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(
            VehicleService vehicleService
    ) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/test")
    public String test() {

        return "AUTO TRACE BACKEND IS RUNNING";
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadVehicle(
            @RequestParam("file")
            MultipartFile file
    ) {

        try {

            if (file.isEmpty()) {

                return ResponseEntity
                        .badRequest()
                        .body("Image is empty.");
            }

            String savedPath =
                    vehicleService
                            .saveVehicleImage(file);

            Map<String, Object> response =
                    new HashMap<>();

            response.put(
                    "success",
                    true
            );

            response.put(
                    "message",
                    "Vehicle image uploaded successfully."
            );

            response.put(
                    "fileName",
                    file.getOriginalFilename()
            );

            response.put(
                    "path",
                    savedPath
            );

            return ResponseEntity.ok(response);

        } catch (Exception error) {

            error.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Failed to upload vehicle image."
                    );
        }
    }
}