package com.undec.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import model.User;
import org.springframework.stereotype.Service;
import output.TokenService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenServiceImp implements TokenService {

    private final String SECRET_KEY = "mi_clave_secreta_super_segura_para_el_proyecto_integrador";
    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes(StandardCharsets.UTF_8)
            );
    @Override
    public String generateAccessToken(User user) {
        // Usamos la librería especial directamente para construir el JWT string
        System.out.println("Entrando a generateAccessToken");
        String token=Jwts.builder()
                .setSubject(user.getEmailUser().getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutos
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("Token generado");
        return token;
    }

    @Override
    public String generateRefreshToken(User user) {

        String token =Jwts.builder()
                .setSubject(user.getEmailUser().getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    @Override
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
