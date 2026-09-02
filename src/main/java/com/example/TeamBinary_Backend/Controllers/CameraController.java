package com.example.TeamBinary_Backend.Controllers;

import com.example.TeamBinary_Backend.DTOs.CameraRequestDTO;
import com.example.TeamBinary_Backend.DTOs.CameraResponseDTO;
import com.example.TeamBinary_Backend.DTOs.CameraStatusUpdateDTO;
import com.example.TeamBinary_Backend.Services.CameraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class CameraController {

    private final CameraService cameraService;

    public CameraController(CameraService cameraService) {
        this.cameraService = cameraService;
    }

    // POST /api/cameras  — Add new camera
    @PostMapping("/cameras")
    public ResponseEntity<CameraResponseDTO> addCamera(@RequestBody CameraRequestDTO dto) {
        try {
            CameraResponseDTO created = cameraService.addCamera(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /api/cameras              — get all
    // GET /api/cameras?status=ACTIVE — filter by status
    // GET /api/cameras?networkId=1   — filter by network
    @GetMapping("/cameras")
    public ResponseEntity<List<CameraResponseDTO>> getAllCameras(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long   networkId
    ) {
        if (status != null) {
            return ResponseEntity.ok(cameraService.getCamerasByStatus(status));
        }
        if (networkId != null) {
            return ResponseEntity.ok(cameraService.getCamerasByNetwork(networkId));
        }
        return ResponseEntity.ok(cameraService.getAllCameras());
    }

    // GET /api/cameras/{id}  — get one camera
    @GetMapping("/{id}")
    public ResponseEntity<CameraResponseDTO> getCameraById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cameraService.getCameraById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH /api/cameras/{id}/status  — update status of one camera
    @PatchMapping("/cameras/{id}/status")
    public ResponseEntity<CameraResponseDTO> updateCameraStatus(
            @PathVariable Long id,
            @RequestBody CameraStatusUpdateDTO dto
    ) {
        try {
            return ResponseEntity.ok(cameraService.updateCameraStatus(id, dto.getStatus()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /api/cameras/{id}  — delete camera
    @DeleteMapping("/cameras/{id}")
    public ResponseEntity<String> deleteCamera(@PathVariable Long id) {
        try {
            cameraService.deleteCamera(id);
            return ResponseEntity.ok("Camera deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
