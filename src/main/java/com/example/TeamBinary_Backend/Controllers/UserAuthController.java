package com.example.TeamBinary_Backend.Controllers;

import com.example.TeamBinary_Backend.DTOs.LoginResponseDto;
import com.example.TeamBinary_Backend.DTOs.UserDto;
import com.example.TeamBinary_Backend.Security.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
        origins = "http://localhost:5173"
)
public class UserAuthController {


    private final AuthService authService;


    public UserAuthController(
            AuthService authService
    ) {

        this.authService =
                authService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody UserDto userDto
    ) {

        try {

            LoginResponseDto response =
                    authService.login(
                            userDto
                    );


            return ResponseEntity.ok(
                    response
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(401)
                    .body(
                            e.getMessage()
                    );
        }
    }
}