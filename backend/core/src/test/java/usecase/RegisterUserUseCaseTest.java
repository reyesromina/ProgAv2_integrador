package usecase;

import exception.ValidationException;
import input.dto.TokenResponse;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import output.PasswordEncoderPort;
import output.TokenService;
import output.UserRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoderPort passwordEncoderPort;
    @Mock
    private TokenService tokenService;

    private Clock fixedClock() {
        return Clock.fixed(
                Instant.parse("2024-01-01T10:00:00Z"),
                ZoneId.systemDefault()
        );
    }

    @Test
    void registerUser_EmailAlreadyExists_ThrowsException() {

        Clock clock = fixedClock();

        when(userRepository.existByEmail("test@gmail.com"))
                .thenReturn(true);

        RegisterUserUseCase useCase =
                new RegisterUserUseCase(userRepository, clock,passwordEncoderPort,tokenService);

        Assertions.assertThrows(
                ValidationException.class,
                () -> useCase.registerUser("test@gmail.com", "123456")
        );

        verify(userRepository, never()).saveUser(any());
    }

    @Test
    void registerUser_ValidUser_SavesUser() {

        Clock clock = fixedClock();

        when(userRepository.existByEmail("test@gmail.com"))
                .thenReturn(false);
        when(passwordEncoderPort.encode("123456")).thenReturn("3213");
        //simula que guarda correctamente
        when(userRepository.saveUser(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(tokenService.generateAccessToken(any(User.class))).thenReturn("tokenAccess");
        when(tokenService.generateRefreshToken(any(User.class))).thenReturn("tokenRefresh");
        RegisterUserUseCase useCase =
                new RegisterUserUseCase(userRepository, clock,passwordEncoderPort,tokenService);
        ;

        TokenResponse result =
                useCase.registerUser("test@gmail.com", "123456");

        Assertions.assertNotNull(result);

        verify(userRepository).saveUser(any(User.class));
    }

    @Test
    void registerUser_SaveFails_ThrowsException() {

        Clock clock = fixedClock();

        when(userRepository.existByEmail("test@gmail.com"))
                .thenReturn(false);
        when(passwordEncoderPort.encode("123456")).thenReturn("3213");
        //simula fallo del repository
        when(userRepository.saveUser(any(User.class)))
                .thenReturn(null);

        RegisterUserUseCase useCase =
                new RegisterUserUseCase(userRepository, clock,passwordEncoderPort,tokenService);
        ;

        Assertions.assertThrows(
                ValidationException.class,
                () -> useCase.registerUser("test@gmail.com", "123456")
        );
    }

    @Test
    void registerUser_EmailNull_ThrowsException() {

        Clock clock = fixedClock();

        when(userRepository.existByEmail(null))
                .thenReturn(false);

        when(passwordEncoderPort.encode("123456")).thenReturn("3213");

        RegisterUserUseCase useCase =
                new RegisterUserUseCase(userRepository, clock,passwordEncoderPort,tokenService);
        ;

        Assertions.assertThrows(
                Exception.class,
                () -> useCase.registerUser(null, "123456")
        );
    }
}