package usecase;

import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;
import output.UserRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetUserByIdUseCaseTest {
    @Mock
    private UserRepository userRepository;


    @Test
    void getUserById_Success() {
        Long id=1L;
        String code = UUID.randomUUID().toString();

        LocalDateTime today= LocalDateTime.of(2024,1,1,2,0);
        User user1 = User.createUserFactory("ROMi@gmail.com","secret123",
                LocalDateTime.now(Clock.systemDefaultZone()),code);

        user1.activate(code,today);

        GetUserByIdUseCase getUserByIdUseCase = new GetUserByIdUseCase(userRepository);
        when(userRepository.findUserById(id)).thenReturn(user1);
        Assertions.assertNotNull(getUserByIdUseCase.getUserById(id));
        Assertions.assertEquals(user1,getUserByIdUseCase.getUserById(id));


    }

    @Test
    void getUserById_NotSuccess_UserDontExist() {
        Long id=1L;
        String code = UUID.randomUUID().toString();

        LocalDateTime today= LocalDateTime.of(2024,1,1,2,0);
        User user1 = User.createUserFactory("ROMi@gmail.com","secret123",
                LocalDateTime.now(Clock.systemDefaultZone()),code);

        user1.activate(code,today);


        GetUserByIdUseCase getUserByIdUseCase = new GetUserByIdUseCase(userRepository);
        //En caso de no exista traer la excepcion proveniente de la bd
        when(userRepository.findUserById(id)).thenReturn(null);
        Assertions.assertThrows(ResourceAccessException.class,()->getUserByIdUseCase.getUserById(id));

    }
}
