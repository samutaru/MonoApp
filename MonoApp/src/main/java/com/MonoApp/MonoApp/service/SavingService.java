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
// Obtener el usuario
User user = userRepository.findById(userId)
.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

int dailyInitial = user.getCigInitial();
double pricePerCig = user.getCigPrice() / 20.0;

// Obtener los registros de ahorro del usuario
List<Saving> records = savingRepository.findAllByUser_Id(userId);

// Calcular el total de cigarrillos evitados, ignorando registros nulos
int totalAvoided = records.stream()
        .filter(s -> s.getCigsSmoked() != null) // Ignorar registros sin valor
        .mapToInt(s -> dailyInitial - s.getCigsSmoked())
        .filter(avoided -> avoided > 0)
        .sum();

// Retornar el dinero ahorrado
return totalAvoided * pricePerCig;


}
public Saving saveDailyMoney(UUID userId, double moneySavedToday) {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    LocalDate today = LocalDate.now();

    Saving todaySaving = savingRepository.findByUser_IdAndDate(userId, today)
            .orElse(new Saving(user, 0, today));

    todaySaving.setSavedMoney(moneySavedToday);

    return savingRepository.save(todaySaving);
}


}
