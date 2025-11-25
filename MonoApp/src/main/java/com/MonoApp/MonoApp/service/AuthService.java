package com.MonoApp.MonoApp.service;

import com.MonoApp.MonoApp.dto.UserLoginDto;
import com.MonoApp.MonoApp.dto.UserRegisterDto;
import com.MonoApp.MonoApp.dto.AuthResponse;
import com.MonoApp.MonoApp.model.User;
import com.MonoApp.MonoApp.repository.UserRepository;
import com.MonoApp.MonoApp.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(UserRegisterDto dto) {

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setMail(dto.getMail());
        user.setName(dto.getName());
        user.setPassword(encoder.encode(dto.getPassword()));

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getId().toString());

        return new AuthResponse(token, user.getId().toString());
    }

    public AuthResponse login(UserLoginDto dto) {

        Optional<User> optional = userRepository.findByMail(dto.getMail());

        if (optional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optional.get();

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getId().toString());

        return new AuthResponse(token, user.getId().toString());
    }
}
