package com.example.TeamBinary_Backend.Controllers;

import com.example.TeamBinary_Backend.DTOs.UserDto;
import com.example.TeamBinary_Backend.Entities.UserEntity;
import com.example.TeamBinary_Backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class UserAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto){
        UserEntity userEntity = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (!passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())){
            return ResponseEntity.status(401).body("Invalid Password");
        }
        return ResponseEntity.ok(userEntity);
    }
}
