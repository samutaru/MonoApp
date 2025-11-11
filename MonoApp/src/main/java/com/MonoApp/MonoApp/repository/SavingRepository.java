package com.MonoApp.MonoApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MonoApp.MonoApp.model.Saving;

import java.util.List;
import java.util.UUID;

public interface SavingRepository extends JpaRepository<Saving, UUID> {
    List<Saving> findByUserId(UUID userId);
}