package com.MonoApp.MonoApp.service;

import com.MonoApp.MonoApp.model.Saving;
import com.MonoApp.MonoApp.model.User;
import com.MonoApp.MonoApp.repository.SavingRepository;
import com.MonoApp.MonoApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SavingService {

    private final SavingRepository savingRepository;
    private final UserRepository userRepository;

    public SavingService(SavingRepository savingRepository, UserRepository userRepository) {
        this.savingRepository = savingRepository;
        this.userRepository = userRepository;
    }

    public Saving registerDailySaving(UUID userId, Integer cigsSmoked) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();

        // ¿Ya hay registro hoy?
        Saving existing = savingRepository.findByUser_IdAndDate(userId, today).orElse(null);

        if (existing != null) {
            existing.setCigsSmoked(cigsSmoked);
            return savingRepository.save(existing);
        }

        // Crear un nuevo registro diario
        Saving newSaving = new Saving(user, cigsSmoked, today);
        return savingRepository.save(newSaving);
    }

    public List<Saving> getHistory(UUID userId) {
        return savingRepository.findAllByUser_Id(userId);
    }

    public double calculateMoneySaved(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();

        int dailyInitial = user.getCigInitial();
        double pricePerPack = user.getCigPrice() / 20.0;

        List<Saving> records = savingRepository.findAllByUser_Id(userId);

        int totalAvoided = records.stream()
                .mapToInt(s -> dailyInitial - s.getCigsSmoked())
                .filter(x -> x > 0)
                .sum();

        return totalAvoided * pricePerPack;
    }
}
