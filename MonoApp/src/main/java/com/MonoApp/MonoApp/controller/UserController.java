package com.MonoApp.MonoApp.controller;

import com.MonoApp.MonoApp.model.User;
import com.MonoApp.MonoApp.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ⬅ Obtiene usuario por ID (requiere JWT)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

   @GetMapping("/me")
public ResponseEntity<User> getMe(Authentication authentication) {
    String userIdStr = authentication.getName(); 
    UUID userId = UUID.fromString(userIdStr);

    User user = userService.getUser(userId);

    return ResponseEntity.ok(user);
}



}
