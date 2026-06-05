package usecase;

import model.User;
import model.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import output.UserRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivateUserScreaduledTest {
    @Mock
    UserRepository userRepository;


    @Test
    void ActivateUserScreaduled_Success(){

       // Instant fixedInstant = Instant.parse("2026-02-14T10:00:00Z");
        Clock fixedClock1 = Clock.fixed(Instant.parse("2026-02-22T10:07:00Z"), ZoneId.of("UTC"));
        Clock fixedClock2= Clock.fixed(Instant.parse("2026-02-14T10:00:00Z"), ZoneId.of("UTC"));
        Clock fixedClock3 = Clock.fixed(Instant.parse("2026-02-23T10:00:00Z"), ZoneId.of("UTC"));
        //para el c.u
        Clock fixedClock4 = Clock.fixed(Instant.parse("2026-02-23T10:00:00Z"), ZoneId.of("UTC"));
        String code = UUID.randomUUID().toString();

        User user = User.createUserFactory(
                "example1@gmail.com",
                "secret123",
                LocalDateTime.now(fixedClock1),
                code
        );
        User user2 = User.createUserFactory(
                "example2@gmail.com",
                "secret123",
                LocalDateTime.now(fixedClock2),
                code
        );
        User user3 = User.createUserFactory(
                "example3@gmail.com",
                "secret123",
                LocalDateTime.now(fixedClock4),
                code
        );

        List<User> users = List.of(user, user2, user3);

        ActivateUserScheduled activateUserScheduled = new ActivateUserScheduled(userRepository,fixedClock4);

        when(userRepository.findUsersByStatusPending()).thenReturn(users);

        activateUserScheduled.activateUser();

        verify(userRepository).saveUserEvaluate(users);

        Assertions.assertEquals(UserStatus.PENDING,users.get(0).getStatus());
        Assertions.assertEquals(UserStatus.EXPIRED,users.get(1).getStatus());
        Assertions.assertEquals(UserStatus.PENDING,users.get(2).getStatus());


    }

}
