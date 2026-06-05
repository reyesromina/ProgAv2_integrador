package usecase;

import exception.ValidationException;
import input.RegisterUserInput;
import input.dto.TokenResponse;
import model.User;
import output.PasswordEncoderPort;
import output.TokenService;
import output.UserRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class RegisterUserUseCase implements RegisterUserInput {

    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenService tokenService;
    private final Clock clock;

    public RegisterUserUseCase(UserRepository userRepository, Clock clock,PasswordEncoderPort passwordEncoder,TokenService tokenService) {
        this.userRepository = userRepository;
        this.clock = clock;
        this.passwordEncoder= passwordEncoder;
        this.tokenService=tokenService;
    }

    @Override
    public TokenResponse registerUser(String email, String password) {

        if (userRepository.existByEmail(email)){
            throw new ValidationException("Email already exists");
        }
        String passwordCifrado = passwordEncoder.encode(password);

        String code = UUID.randomUUID().toString();

        User user1 = User.createUserFactory(email,passwordCifrado,
                LocalDateTime.now(clock),code);

        User savedUser = userRepository.saveUser(user1);

        if (savedUser == null) {
            throw new ValidationException("User could not be saved");
        }

    //    System.out.println("Usuario guardado");

     //   System.out.println("Generando access token");
        String accessToken = tokenService.generateAccessToken(savedUser);

    //    System.out.println("Access token generado");

    //    System.out.println("Generando refresh token");
        String refreshToken = tokenService.generateRefreshToken(savedUser);
    //    System.out.println("Refresh token generado");
        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);

        return tokenResponse;
    }
}