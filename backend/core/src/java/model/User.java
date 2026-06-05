package model;

import exception.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class User {

    private  Long id;
    private final EmailValueObject email;
    private final String password;
    private  UserStatus status;
    private  String activationCode;
    private  LocalDateTime activationExpiredAt;
    private final LocalDateTime createdAt;

    private User(Long id,
                EmailValueObject email,
                String password,
                UserStatus status,
                String activationCode,
                LocalDateTime activationExpiredAt,
                LocalDateTime createdAt) {

        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.activationCode = activationCode;
        this.activationExpiredAt = activationExpiredAt;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
    public EmailValueObject getEmailUser() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public LocalDateTime getActivationExpiredAt() {
        return activationExpiredAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void activate(String codeActivation,LocalDateTime today){
        if(isExpired(today)){
            status= UserStatus.EXPIRED;
            throw new ValidationException("Expired code");
        }
        if(!codeActivation.equals(activationCode)){

            throw new ValidationException("Incorrect code");
        }

        if(!isExpired(today) && codeActivation.equals(activationCode) )
             status=UserStatus.ACTIVE;
    }

    private boolean isExpired(LocalDateTime today) {
        if((today.isAfter(activationExpiredAt)))
            return true;

        return false;
    }

    public void evaluateExpired(LocalDateTime today){
        if(today.isAfter(activationExpiredAt) ){
            status=UserStatus.EXPIRED;
        }

    }

    public static User createUserFactory(String email,
                                     String password,LocalDateTime createdAt, String activationCode
                                     ) {
        if(password == null){
            throw new ValidationException("Password is null");
        }

        EmailValueObject emailValid= EmailValueObject.createEmail(email);


        LocalDateTime activationExpiredAt = createdAt.plusDays(1);


        return new User(null,
                emailValid
                ,password,
                UserStatus.PENDING,
                activationCode,
                activationExpiredAt,
                createdAt);
    }

    public static User constructor(Long id,
                                   String email,
                                   String password,
                                   UserStatus status,
                                   String activationCode,
                                   LocalDateTime activationExpiredAt,
                                   LocalDateTime createdAt) {

        EmailValueObject emailValid= EmailValueObject.createEmail(email);

        return new User(id,emailValid,password,status,activationCode,activationExpiredAt,createdAt);
    }
}
