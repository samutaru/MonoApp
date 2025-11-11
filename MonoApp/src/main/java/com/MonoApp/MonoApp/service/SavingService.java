package com.MonoApp.MonoApp.service;

import org.springframework.stereotype.Service;

import com.MonoApp.MonoApp.model.Saving;
import com.MonoApp.MonoApp.repository.SavingRepository;

import java.util.List;
import java.util.UUID;

@Service
public class SavingService {

    private final SavingRepository savingRepository;

    public SavingService(SavingRepository savingRepository) {
        this.savingRepository = savingRepository;
    }

    public List<Saving> getAllSavings() {
        return savingRepository.findAll();
    }

    public List<Saving> getSavingsByUserId(UUID userId) {
        return savingRepository.findByUserId(userId);
    }

    public Saving createSaving(Saving saving) {
        return savingRepository.save(saving);
    }

    public void deleteSaving(UUID id) {
        savingRepository.deleteById(id);
    }
}