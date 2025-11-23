package com.MonoApp.MonoApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.MonoApp.MonoApp.model.Saving;
import com.MonoApp.MonoApp.service.SavingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/savings")
public class SavingController {

    @Autowired
    private SavingService savingService;

    @PostMapping("/{userId}/add")
    public ResponseEntity<Saving> addSaving(
            @PathVariable UUID userId,
            @RequestBody Saving savingData
    ) {
        return ResponseEntity.ok(
                savingService.registerSaving(
                        userId,
                        savingData.getSavedMoney(),
                        savingData.getDaysWithoutSmoking()
                )
        );
    }

    @GetMapping("/{userId}/total")
    public ResponseEntity<Double> getTotal(@PathVariable UUID userId) {
        return ResponseEntity.ok(savingService.getTotalSavings(userId));
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<Saving>> getHistory(@PathVariable UUID userId) {
        return ResponseEntity.ok(savingService.getHistory(userId));
    }
}
