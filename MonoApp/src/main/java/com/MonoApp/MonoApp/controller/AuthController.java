package com.MonoApp.MonoApp.controller;


import com.MonoApp.MonoApp.dto.*;
import com.MonoApp.MonoApp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
private final AuthService authService;
public AuthController(AuthService authService){ this.authService = authService; }


@PostMapping("/register")
public ResponseEntity<AuthResponse> register(@RequestBody UserRegisterDto dto) {
return ResponseEntity.ok(authService.register(dto));
}


@PostMapping("/login")
public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
return ResponseEntity.ok(authService.login(req));
}


@PostMapping("/refresh")
public ResponseEntity<AuthResponse> refresh(@RequestBody String refreshToken){
return ResponseEntity.ok(authService.refresh(refreshToken));
}


@PostMapping("/logout")
public ResponseEntity<Void> logout(@RequestBody String refreshToken){
authService.logout(refreshToken); return ResponseEntity.ok().build();
}
}