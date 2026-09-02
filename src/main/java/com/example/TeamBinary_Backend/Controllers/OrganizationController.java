package com.example.TeamBinary_Backend.Controllers;

import com.example.TeamBinary_Backend.DTOs.CreateOrganizationDto;
import com.example.TeamBinary_Backend.DTOs.OrganizationResponseDto;
import com.example.TeamBinary_Backend.DTOs.OrganizationStatusDto;
import com.example.TeamBinary_Backend.Services.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@CrossOrigin(origins = "http://localhost:5173")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    public ResponseEntity<?> createOrganization(@RequestBody CreateOrganizationDto dto) {
        try {
            OrganizationResponseDto response = organizationService.createOrganization(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<?> getAllOrganizations() {

        try {
            List<OrganizationResponseDto> organizations = organizationService.getAllOrganizations();

            return ResponseEntity.ok(organizations);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizationById(@PathVariable String id) {

        try {
            OrganizationResponseDto organization = organizationService.getOrganizationById(id);

            return ResponseEntity.ok(organization);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody OrganizationStatusDto dto) {

        try {
            OrganizationResponseDto organization = organizationService.updateOrganizationStatus(id, dto.getActive());

            return ResponseEntity.ok(organization);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
