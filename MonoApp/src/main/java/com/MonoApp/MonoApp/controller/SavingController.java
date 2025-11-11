package com.MonoApp.MonoApp.controller;

import org.springframework.web.bind.annotation.*;

import com.MonoApp.MonoApp.model.Saving;
import com.MonoApp.MonoApp.service.SavingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/savings")
public class SavingController {

    private final SavingService savingService;

    public SavingController(SavingService savingService) {
        this.savingService = savingService;
    }

    @GetMapping
    public List<Saving> getAllSavings() {
        return savingService.getAllSavings();
    }

    @GetMapping("/user/{userId}")
    public List<Saving> getSavingsByUser(@PathVariable UUID userId) {
        return savingService.getSavingsByUserId(userId);
    }

    @PostMapping
    public Saving createSaving(@RequestBody Saving saving) {
        return savingService.createSaving(saving);
    }

    @DeleteMapping("/{id}")
    public void deleteSaving(@PathVariable UUID id) {
        savingService.deleteSaving(id);
    }
}
