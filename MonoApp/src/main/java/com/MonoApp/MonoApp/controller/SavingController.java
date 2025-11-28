package com.MonoApp.MonoApp.controller;

import com.MonoApp.MonoApp.dto.SavingRequestDto;
import com.MonoApp.MonoApp.model.Saving;
import com.MonoApp.MonoApp.service.SavingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/savings")
public class SavingController {

    private final SavingService savingService;

    public SavingController(SavingService savingService) {
        this.savingService = savingService;
    }

    @PostMapping("/{userId}/daily")
    public ResponseEntity<Saving> registerDaily(
            @PathVariable UUID userId,
            @RequestBody SavingRequestDto dto
    ) {
        return ResponseEntity.ok(
                savingService.registerDailySaving(userId, dto.getCigsSmoked())
        );
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<Saving>> getHistory(@PathVariable UUID userId) {
        return ResponseEntity.ok(savingService.getHistory(userId));
    }

    @GetMapping("/{userId}/money-saved")
    public ResponseEntity<Double> getMoneySaved(@PathVariable UUID userId) {
        return ResponseEntity.ok(savingService.calculateMoneySaved(userId));
    }
}
