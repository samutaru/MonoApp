package com.MonoApp.MonoApp.repository;
import com.MonoApp.MonoApp.model.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface SavingRepository extends JpaRepository<Saving, UUID> {
    @Query("SELECT COALESCE(SUM(s.savedMoney),0) FROM Saving s WHERE s.user.id = :userId")
    Double getTotalSavings(@Param("userId") UUID userId);

    List<Saving> findAllByUser_Id(UUID userId);
    Optional<Saving> findByUser_IdAndDate(UUID userId, LocalDate date);
    List<Saving> findByUser_Id(UUID userId);
}
