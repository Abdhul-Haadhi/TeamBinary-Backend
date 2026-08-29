package com.example.TeamBinary_Backend.Security;

import com.example.TeamBinary_Backend.DTOs.LoginResponseDto;
import com.example.TeamBinary_Backend.DTOs.UserDto;

public interface AuthService {

    LoginResponseDto login(UserDto userDto);

}
