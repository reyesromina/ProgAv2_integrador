package com.undec.persistence.entity;

import jakarta.persistence.*;
import model.EmailValueObject;
import model.UserStatus;

import java.time.LocalDateTime;
@Entity
@Table(name="USER")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private  Long id;
    //como hacerlo?
    private  String email;
    @Column(name="password")
    private  String password;
    @Column(name="status")
    private  UserStatus status;
    @Column(name="Activation Code")
    private  String activationCode;
    @Column(name="Activation Expired At")
    private  LocalDateTime activationExpiredAt;
    @Column(name="Created At")
    private  LocalDateTime createdAt;

    public UserData() {
    }

    public UserData(Long id, EmailValueObject email, String password, UserStatus status, String activationCode, LocalDateTime createdAt, LocalDateTime activationExpiredAt) {
        this.id = id;
        this.email = email.getEmail();
        this.password = password;
        this.status = status;
        this.activationCode = activationCode;
        this.createdAt = createdAt;
        this.activationExpiredAt = activationExpiredAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    public String getActivationCode() {
        return activationCode;
    }
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
    public LocalDateTime getActivationExpiredAt() {
        return activationExpiredAt;
    }
    public void setActivationExpiredAt(LocalDateTime activationExpiredAt) {
        this.activationExpiredAt = activationExpiredAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
