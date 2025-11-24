    package com.MonoApp.MonoApp.service;


    import com.MonoApp.MonoApp.dto.*;
    import com.MonoApp.MonoApp.model.User;
    import com.MonoApp.MonoApp.repository.UserRepository;
    import com.MonoApp.MonoApp.security.JwtUtil;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;
    import java.util.concurrent.ConcurrentHashMap;


    @Service
    public class AuthService {
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    // simple refresh token store (in-memory). Replace with DB for production.
    private final ConcurrentHashMap<String, String> refreshStore = new ConcurrentHashMap<>();


    public AuthService(UserRepository userRepo, JwtUtil jwtUtil){ this.userRepo = userRepo; this.jwtUtil = jwtUtil; }


public AuthRegisterResponse register(UserRegisterDto reg) {
    var u = new User();
    u.setName(reg.getName());
    u.setMail(reg.getMail());
    u.setPassword(encoder.encode(reg.getPassword()));
    u.setCigInitial(reg.getCigInitial());
    u.setCigPrice(reg.getCigPrice());
    u = userRepo.save(u);

    String access = jwtUtil.generateAccessToken(u.getId().toString());
    String refresh = jwtUtil.generateRefreshToken(u.getId().toString());
    refreshStore.put(refresh, u.getId().toString());

    return new AuthRegisterResponse(u, access, refresh);
}





    public AuthResponse login(LoginRequest req) {
    var user = userRepo.findByMail(req.getMail()).orElseThrow(() -> new RuntimeException("Invalid credentials"));
    if (!encoder.matches(req.getPassword(), user.getPassword())) throw new RuntimeException("Invalid credentials");
    String access = jwtUtil.generateAccessToken(user.getId().toString());
    String refresh = jwtUtil.generateRefreshToken(user.getId().toString());
    refreshStore.put(refresh, user.getId().toString());
    return new AuthResponse(access, refresh);
    }


    public AuthResponse refresh(String refreshToken) {
    if (!jwtUtil.validate(refreshToken)) throw new RuntimeException("Invalid refresh token");
    String userId = jwtUtil.getUserIdFromToken(refreshToken);
    // optional: check the refresh exists
    if (!refreshStore.containsKey(refreshToken)) throw new RuntimeException("Refresh token revoked");
    String newAccess = jwtUtil.generateAccessToken(userId);
    String newRefresh = jwtUtil.generateRefreshToken(userId);
    refreshStore.remove(refreshToken);
    refreshStore.put(newRefresh, userId);
    return new AuthResponse(newAccess, newRefresh);
    }


    public void logout(String refreshToken) { refreshStore.remove(refreshToken); }
    }