package com.example.TeamBinary_Backend.Controllers;

import com.example.TeamBinary_Backend.DTOs.CreateInvestigatorDto;
import com.example.TeamBinary_Backend.DTOs.InvestigatorDto;
import com.example.TeamBinary_Backend.DTOs.UpdateInvestigatorDto;
import com.example.TeamBinary_Backend.Services.InvestigatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investigators")
@CrossOrigin(origins = "http://localhost:5173")
public class InvestigatorController {

    private final InvestigatorService investigatorService;

    public InvestigatorController(InvestigatorService investigatorService) {
        this.investigatorService = investigatorService;
    }

    @PostMapping
    public ResponseEntity<?> createInvestigator(@RequestBody CreateInvestigatorDto dto) {

        try {
            InvestigatorDto investigator = investigatorService.createInvestigator(dto);


            return ResponseEntity.status(HttpStatus.CREATED).body(investigator);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    // GET ALL INVESTIGATORS
    @GetMapping
    public ResponseEntity<?> getInvestigators() {

        try {
            List<InvestigatorDto> investigators = investigatorService.getMyOrganizationInvestigators();


            return ResponseEntity.ok(investigators);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    // GET ONE
    @GetMapping("/{userId}")
    public ResponseEntity<?> getInvestigator(@PathVariable Long userId) {

        try {
            InvestigatorDto investigator = investigatorService.getInvestigatorById(userId);

            return ResponseEntity.ok(investigator);

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    // =========================================================
    // UPDATE
    // =========================================================

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateInvestigator(@PathVariable Long userId, @RequestBody UpdateInvestigatorDto dto) {

        try {
            InvestigatorDto investigator = investigatorService.updateInvestigator(userId, dto);

            return ResponseEntity.ok(investigator);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    // DELETE
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteInvestigator(@PathVariable Long userId) {

        try {
            investigatorService.deleteInvestigator(userId);

            return ResponseEntity.ok("Investigator deleted successfully");

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
