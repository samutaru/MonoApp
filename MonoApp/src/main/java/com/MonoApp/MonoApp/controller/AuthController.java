package com.MonoApp.MonoApp.controller;

import com.MonoApp.MonoApp.dto.UserLoginDto;
import com.MonoApp.MonoApp.dto.UserRegisterDto;
import com.MonoApp.MonoApp.dto.AuthResponse;
import com.MonoApp.MonoApp.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody UserRegisterDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserLoginDto dto) {
        return authService.login(dto);
    }
}

 
