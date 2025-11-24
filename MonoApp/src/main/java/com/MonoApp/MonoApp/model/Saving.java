package com.MonoApp.MonoApp.model;

import jakarta.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "savings")
public class Saving {
@Id
@GeneratedValue
private UUID id;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private User user;


private Double savedMoney;
private Integer daysWithoutSmoking;


public Saving(UUID id, User user, Double savedMoney, Integer daysWithoutSmoking) {
    this.id = id;
    this.user = user;
    this.savedMoney = savedMoney;
    this.daysWithoutSmoking = daysWithoutSmoking;
}
public Saving() {
}
public UUID getId() {
    return id;
}
public void setId(UUID id) {
    this.id = id;
}
public User getUser() {
    return user;
}
public void setUser(User user) {
    this.user = user;
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


// getters/setters

}