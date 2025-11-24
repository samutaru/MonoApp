package com.MonoApp.MonoApp.controller;

import com.MonoApp.MonoApp.model.User;
import com.MonoApp.MonoApp.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

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

    // ⬅ Muestra el usuario autenticado (JWT)
    @GetMapping("/me")
    public ResponseEntity<User> getMe() {

        String userId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(
                userService.getUser(UUID.fromString(userId))
        );
    }
}
