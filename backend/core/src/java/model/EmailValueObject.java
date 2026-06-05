package model;

import exception.EmailException;

import java.util.Objects;
import java.util.regex.Pattern;

public class EmailValueObject {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    private final String email;

    private EmailValueObject(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailValueObject email = (EmailValueObject) o;
        return Objects.equals(email, email.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
    public String getEmail() {
        return email;
    }

    public static EmailValueObject createEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new EmailException("El email no puede estar vacío.");
        }

        String normalizedValue = email.trim().toLowerCase();

        if (!PATTERN.matcher(normalizedValue).matches()) {
            throw new EmailException("El formato del email es inválido.");
        }

        return new EmailValueObject(normalizedValue);
    }



}
