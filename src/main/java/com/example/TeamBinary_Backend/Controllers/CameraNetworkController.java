package com.example.TeamBinary_Backend.Controllers;

import com.example.TeamBinary_Backend.DTOs.CameraNetworkRequestDTO;
import com.example.TeamBinary_Backend.DTOs.CameraNetworkResponseDTO;
import com.example.TeamBinary_Backend.DTOs.NetworkStatusUpdateDTO;
import com.example.TeamBinary_Backend.Services.CameraNetworkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/networks")
public class CameraNetworkController {

    private final CameraNetworkService networkService;

    public CameraNetworkController(CameraNetworkService networkService) {
        this.networkService = networkService;
    }

    // POST /api/networks  — create new network
    @PostMapping("/add-network")
    public ResponseEntity<CameraNetworkResponseDTO> createNetwork(
            @RequestBody CameraNetworkRequestDTO dto
    ) {
        try {
            CameraNetworkResponseDTO created = networkService.createNetwork(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /api/networks  — get all networks
    @GetMapping("")
    public ResponseEntity<List<CameraNetworkResponseDTO>> getAllNetworks() {
        return ResponseEntity.ok(networkService.getAllNetworks());
    }

    // GET /api/networks/{id}  — get one network
    @GetMapping("/{id}")
    public ResponseEntity<CameraNetworkResponseDTO> getNetworkById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(networkService.getNetworkById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH /api/networks/{id}/status  — update network status
    @PatchMapping("/{id}/status")
    public ResponseEntity<CameraNetworkResponseDTO> updateNetworkStatus(
            @PathVariable Long id,
            @RequestBody NetworkStatusUpdateDTO dto
    ) {
        try {
            return ResponseEntity.ok(networkService.updateNetworkStatus(id, dto.getStatus()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /api/networks/{id}  — delete network
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNetwork(@PathVariable Long id) {
        try {
            networkService.deleteNetwork(id);
            return ResponseEntity.ok("Network deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
