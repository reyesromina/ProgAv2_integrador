package usecase;

import exception.CredencialesInvalidasException;
import exception.ValidationException;
import input.LoginUserInput;
import input.dto.TokenResponse;
import model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import output.PasswordEncoderPort;
import output.TokenService;
import output.UserRepository;

public class LoginUserUseCase implements LoginUserInput {
    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenService tokenService;

    public LoginUserUseCase(UserRepository userRepository, PasswordEncoderPort passwordEncoderPort, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenService = tokenService;
    }

    @Override
    public TokenResponse login(String email, String password) {

        User user=userRepository.findByEmail(email);

        if(user==null){
            throw new CredencialesInvalidasException("Credenciales Invalidas email");
        }
        if(!passwordEncoderPort.matches(password, user.getPassword())){
            throw new CredencialesInvalidasException("Credenciales Invalidas password");
        }

        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);
        return tokenResponse;
    }
}
