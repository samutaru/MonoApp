package com.MonoApp.MonoApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.MonoApp.MonoApp.model.User;
import com.MonoApp.MonoApp.repository.UserRepository;
import java.sql.Date;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService { // ← Implementar la interfaz
    
    @Autowired
    private UserRepository userRepo;
    
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
}