package com.MonoApp.MonoApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.MonoApp.MonoApp.dto.SavingDto;
import com.MonoApp.MonoApp.model.Saving;
import com.MonoApp.MonoApp.service.SavingService;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/savings")
public class SavingController {

    @Autowired
    private SavingService savingService;

    @PostMapping("/add")
public ResponseEntity<Saving> addSaving(
        @RequestBody SavingDto savingData,
        Authentication authentication
) {
    UUID userId = UUID.fromString(authentication.getName());
    return ResponseEntity.ok(
            savingService.registerSaving(
                    userId,
                    savingData.getSavedMoney(),
                    savingData.getDaysWithoutSmoking()
            )
    );
}


    @GetMapping("/total")
    public ResponseEntity<Double> getTotal(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(savingService.getTotalSavings(userId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Saving>> getHistory(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(savingService.getHistory(userId));
    }
}
