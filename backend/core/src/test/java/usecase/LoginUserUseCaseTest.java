package usecase;

import input.dto.TokenResponse;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import output.PasswordEncoderPort;
import output.TokenService;
import output.UserRepository;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginUserUseCaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoderPort passwordEncoderPort;
    @Mock
    private TokenService tokenService;

    @Test
    public void loginUserTest(){
        User user= User.createUserFactory(
                "emailExample@gmail.com",
                "1234", LocalDateTime.now(Clock.systemDefaultZone()),
                "3333");

        when(userRepository.findByEmail("emailExample@gmail.com")).thenReturn(user);
        when(passwordEncoderPort.matches("1234",user.getPassword())).thenReturn(true);
        when(tokenService.generateAccessToken(user)).thenReturn("accessToken");
        when(tokenService.generateRefreshToken(user)).thenReturn("refreshToken");

        LoginUserUseCase useCase=new LoginUserUseCase(userRepository,passwordEncoderPort,tokenService);
        TokenResponse token=useCase.login("emailExample@gmail.com","1234");

        Assertions.assertNotNull(token);
        Assertions.assertEquals("accessToken", token.getAccessToken());
        Assertions.assertEquals("refreshToken", token.getRefreshToken());


    }
}
