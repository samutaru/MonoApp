package com.MonoApp.MonoApp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
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

    private Integer cigsSmoked; // cigarrillos fumados ese día
    private Double savedMoney;
    private Integer daysWithoutSmoking;
    private LocalDate date; // fecha del registro diario

    public Saving() {}

    public Saving(User user, Integer cigsSmoked, LocalDate date,double savedMoney, Integer daysWithoutSmoking) {
        this.user = user;
        this.cigsSmoked = cigsSmoked;
        this.date = date;
        this.savedMoney = savedMoney;
        this.daysWithoutSmoking = daysWithoutSmoking;
    }
       public Saving(User user, Integer cigsSmoked, LocalDate date) {
        this.user = user;
        this.cigsSmoked = cigsSmoked;
        this.date = date;
        // opcional: calcular savedMoney inicial aquí si tienes precio y baseline en user
        if (user != null && user.getCigInitial() != null && user.getCigPrice() != null) {
            int avoided = Math.max(0, user.getCigInitial() - cigsSmoked);
            this.savedMoney = (double) (avoided * user.getCigPrice());
        } else {
            this.savedMoney = 0.0;
        }
    }
    public UUID getId() { return id; }
    public User getUser() { return user; }
    public Integer getCigsSmoked() { return cigsSmoked; }
    public LocalDate getDate() { return date; }
    public Double getSavedMoney() { return savedMoney; }
    public Integer getDaysWithoutSmoking() { return daysWithoutSmoking; }

    public void setId(UUID id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setCigsSmoked(Integer cigsSmoked) { this.cigsSmoked = cigsSmoked; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setSavedMoney(Double savedMoney) { this.savedMoney = savedMoney; }
    public void setDaysWithoutSmoking(Integer daysWithoutSmoking) { this.daysWithoutSmoking = daysWithoutSmoking; }
}
