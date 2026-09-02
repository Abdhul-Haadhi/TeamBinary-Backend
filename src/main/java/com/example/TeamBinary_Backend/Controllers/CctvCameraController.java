package com.example.TeamBinary_Backend.Controllers;

import com.example.TeamBinary_Backend.DTOs.CctvCameraDto;
import com.example.TeamBinary_Backend.Services.CctvCameraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/camerass")
public class CctvCameraController {

    private final CctvCameraService cameraService;

    public CctvCameraController(CctvCameraService cameraService) {
        this.cameraService = cameraService;
    }

    @PostMapping
    public ResponseEntity<CctvCameraDto> createCamera(
            @RequestBody CctvCameraDto cameraDto
    ) {

        return ResponseEntity.ok(
                cameraService.createCamera(cameraDto)
        );
    }


    @GetMapping
    public ResponseEntity<List<CctvCameraDto>>
    getAllCameras() {

        return ResponseEntity.ok(
                cameraService.getAllCameras()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<CctvCameraDto>
    getCameraById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                cameraService.getCameraById(id)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCamera(
            @PathVariable Long id
    ) {

        cameraService.deleteCamera(id);

        return ResponseEntity.noContent().build();
    }
}
