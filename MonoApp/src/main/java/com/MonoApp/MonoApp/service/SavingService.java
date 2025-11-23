package com.MonoApp.MonoApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MonoApp.MonoApp.model.Saving;
import com.MonoApp.MonoApp.model.User;
import com.MonoApp.MonoApp.repository.SavingRepository;
import com.MonoApp.MonoApp.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class SavingService {

    @Autowired
    private SavingRepository savingRepo;

    @Autowired
    private UserRepository userRepo;

    public Saving registerSaving(UUID userId, Double amount, Integer days) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Saving s = new Saving();
        s.setUser(user);
        s.setSavedMoney(amount);
        s.setDaysWithoutSmoking(days);

        return savingRepo.save(s);
    }

    public Double getTotalSavings(UUID userId) {
        return savingRepo.getTotalSavings(userId);
    }

    public List<Saving> getHistory(UUID userId) {
        return savingRepo.findByUserId(userId);
    }
}
