package com.undec.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.User;
import model.UserStatus;

import java.time.LocalDateTime;

public class UserResponse {

    @JsonProperty("id")
    private  Long id;
    @JsonProperty("email")
    private String email;

    @JsonProperty("statusUser")
    private UserStatus status;

    @JsonProperty("activationExpiredAt")
    private LocalDateTime activationExpiredAt;
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    public UserResponse() {
    }

    public UserResponse(Long id,
                        String email,

                        UserStatus status,

                        LocalDateTime activationExpiredAt,
                        LocalDateTime createdAt) {
        this.id = id;
        this.email = email;

        this.status = status;

        this.activationExpiredAt = activationExpiredAt;
        this.createdAt = createdAt;
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

    public UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus status) {
        this.status = status;
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

    public static UserResponse fromDomainUser (User user){
        return new UserResponse(
                user.getId(),
                user.getEmailUser().getEmail()
                ,user.getStatus()
                ,user.getActivationExpiredAt()
                ,user.getCreatedAt());
    }
}
