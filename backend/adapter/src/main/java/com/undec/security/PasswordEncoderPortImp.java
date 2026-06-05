package com.undec.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import output.PasswordEncoderPort;

@Component
public class PasswordEncoderPortImp implements PasswordEncoderPort {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordEncoderPortImp() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }
    @Override
    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public boolean matches(String passwordIngressed, String passwordHashUser) {
        return bCryptPasswordEncoder.matches(passwordIngressed,passwordHashUser);
    }
}
