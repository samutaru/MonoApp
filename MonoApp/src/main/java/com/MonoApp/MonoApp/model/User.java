package com.MonoApp.MonoApp.model;

import jakarta.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    // NUEVOS CAMPOS
    private Integer cigsSmokedToday = 0;   // contador diario
    private Double totalMoneySaved = 0.0;  // acumulado total

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String mail;

    @Column(nullable = false)
    private String password; // hashed

    private java.sql.Date registerDate;

    private Float cigPrice;       // precio por cigarro
    private Integer cigInitial;   // cigarrillos fumados antes de empezar
    private Boolean mascotStatus = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // evita loops infinitos en JSON
    private List<Saving> savings = new ArrayList<>();


    // ---------------------
    // GETTERS Y SETTERS
    // ---------------------

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getCigsSmokedToday() {
        return cigsSmokedToday;
    }

    public void setCigsSmokedToday(Integer cigsSmokedToday) {
        this.cigsSmokedToday = cigsSmokedToday;
    }

    public Double getTotalMoneySaved() {
        return totalMoneySaved;
    }

    public void setTotalMoneySaved(Double totalMoneySaved) {
        this.totalMoneySaved = totalMoneySaved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public java.sql.Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(java.sql.Date registerDate) {
        this.registerDate = registerDate;
    }

    public Float getCigPrice() {
        return cigPrice;
    }

    public void setCigPrice(Float cigPrice) {
        this.cigPrice = cigPrice;
    }

    public Integer getCigInitial() {
        return cigInitial;
    }

    public void setCigInitial(Integer cigInitial) {
        this.cigInitial = cigInitial;
    }

    public Boolean getMascotStatus() {
        return mascotStatus;
    }

    public void setMascotStatus(Boolean mascotStatus) {
        this.mascotStatus = mascotStatus;
    }

    public List<Saving> getSavings() {
        return savings;
    }

    public void setSavings(List<Saving> savings) {
        this.savings = savings;
    }
}
