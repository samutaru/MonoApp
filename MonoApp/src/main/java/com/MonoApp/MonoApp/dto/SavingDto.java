package com.MonoApp.MonoApp.dto;
import java.util.UUID;
public class SavingDto { public UUID id; 
    public UUID userId; 
    public Double savedMoney; 
    public Integer daysWithoutSmoking;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public Double getSavedMoney() {
        return savedMoney;
    }
    public void setSavedMoney(Double savedMoney) {
        this.savedMoney = savedMoney;
    }
    public Integer getDaysWithoutSmoking() {
        return daysWithoutSmoking;
    }
    public void setDaysWithoutSmoking(Integer daysWithoutSmoking) {
        this.daysWithoutSmoking = daysWithoutSmoking;
    } 

    

}
