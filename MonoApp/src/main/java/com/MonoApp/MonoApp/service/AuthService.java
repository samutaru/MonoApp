package com.MonoApp.MonoApp.service;

import com.MonoApp.MonoApp.dto.UserLoginDto;
import com.MonoApp.MonoApp.dto.UserRegisterDto;
import com.MonoApp.MonoApp.dto.AuthResponse;
import com.MonoApp.MonoApp.model.Saving;
import com.MonoApp.MonoApp.model.User;
import com.MonoApp.MonoApp.repository.SavingRepository;
import com.MonoApp.MonoApp.repository.UserRepository;
import com.MonoApp.MonoApp.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthService {

    private final SavingRepository savingRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(SavingRepository savingRepository,UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.savingRepository = savingRepository;
    }

    @Transactional
public AuthResponse register(UserRegisterDto dto) {

    // 1️⃣ Creamos el usuario
    User user = new User();
    user.setMail(dto.getMail());
    user.setName(dto.getName());
    user.setPassword(encoder.encode(dto.getPassword()));
    user.setRegisterDate(java.sql.Date.valueOf(LocalDate.now()));
    user.setCigPrice(dto.getCigPrice());
    user.setCigInitial(dto.getCigInitial());


    // 2️⃣ Guardamos el usuario y obtenemos la entidad persistida
    User savedUser = userRepository.save(user);

    // 3️⃣ Creamos un Saving inicial asociado al usuario
    Saving initialSaving = new Saving();
    initialSaving.setUser(savedUser);
    initialSaving.setSavedMoney(0.0);
    initialSaving.setDaysWithoutSmoking(0);

    savingRepository.save(initialSaving);

    // 4️⃣ Generamos el token JWT usando el ID generado
    String token = jwtUtil.generateToken(savedUser.getId().toString());

    // 5️⃣ Devolvemos la respuesta
    return new AuthResponse(token, savedUser.getId().toString());
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
