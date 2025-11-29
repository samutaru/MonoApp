package com.MonoApp.MonoApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.MonoApp.MonoApp.model.User;
import com.MonoApp.MonoApp.repository.UserRepository;
import java.sql.Date;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService { // ← Implementar la interfaz
    
    @Autowired
    public UserRepository userRepo;
    private final SavingService savingService;
    public UserService(UserRepository userRepo, SavingService savingService) {
    this.userRepo = userRepo;
    this.savingService = savingService;
}
    // Tus métodos existentes
    public User createUser(User user) {
        user.setRegisterDate(new Date(System.currentTimeMillis()));
        return userRepo.save(user);
    }
    
    public User getUser(UUID id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    // ⭐ NUEVO: Método requerido por UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepo.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + name));
        
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getName())
                .password(user.getPassword())
                .authorities("ROLE_USER") // Ajusta según tus roles
                .build();
    }

        public User updateCigInitial(UUID userId, Integer cigInitial) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setCigInitial(cigInitial);
        return userRepo.save(user);
    }

    public User updateCigInitialById(UUID id, Integer cigInitial) {
    User user = userRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
            ));

    user.setCigInitial(cigInitial);
    return userRepo.save(user);
}
public User addDailyCig(UUID userId) {

    User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    user.setCigsSmokedToday(user.getCigsSmokedToday() + 1);

    return userRepo.save(user);
}
public void finishDay(UUID userId) {

    User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    int cigsBefore = user.getCigInitial();
    int cigsNow = user.getCigsSmokedToday();

    int cigsAvoided = Math.max(cigsBefore - cigsNow, 0);

    double moneySavedToday = cigsAvoided * user.getCigPrice();

    // sumar al total
    user.setTotalMoneySaved(user.getTotalMoneySaved() + moneySavedToday);

    // reiniciar contador diario
    user.setCigsSmokedToday(0);

    userRepo.save(user);

    // 🔥 guardar el ahorro diario en Saving
    savingService.saveDailyMoney(userId, moneySavedToday);
}




}