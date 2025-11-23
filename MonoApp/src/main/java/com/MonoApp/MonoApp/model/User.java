package com.MonoApp.MonoApp.model;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.MonoApp.MonoApp.repository.UserRepository;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(unique = true)
    private String mail;

    private String password;

    private java.sql.Date registerDate;

    private Float cigPrice;

    private Integer cigInitial;

    private Boolean mascotStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Saving> savings = new ArrayList<>();
  

    // Constructors, getters, and setters
    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

