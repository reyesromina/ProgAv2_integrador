package model;

import exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.UUID;


public class TestUser {
    @Test
    void testUser_Success() {
        User user= User.createUserFactory("romi@gmail.com","secret123",
                LocalDateTime.now(Clock.systemDefaultZone()),null);

        Assertions.assertNotNull(user);
    }
    @Test
    void instanceUser_AllAttributes_InstanceCorrect() {

        Clock fixedClock1 = Clock.fixed(Instant.parse("2026-02-22T10:07:00Z"), ZoneId.of("UTC"));
        User user1 = User.createUserFactory("ROMi@gmail.com","secret123",
                LocalDateTime.now(fixedClock1),null);



      Assertions.assertEquals("romi@gmail.com",user1.getEmailUser().getEmail());
      Assertions.assertEquals("secret123",user1.getPassword());
      Assertions.assertEquals(LocalDateTime.now(fixedClock1),user1.getCreatedAt());
      Assertions.assertEquals(UserStatus.PENDING,user1.getStatus());
      Assertions.assertEquals(LocalDateTime.now(fixedClock1).plusDays(1), user1.getActivationExpiredAt());
    }
    @Test
    void instanceUser_AllAttributes_InstanceCorrect_2() {
        String activateCode= UUID.randomUUID().toString();

        Clock fixedClock1 = Clock.fixed(Instant.parse("2026-02-22T10:07:00Z"), ZoneId.of("UTC"));
        LocalDateTime today= LocalDateTime.of(2024,1,1,2,0);
        User user1 = User.createUserFactory("ROMi@gmail.com","secret123",
                LocalDateTime.now(fixedClock1),activateCode);

        user1.activate(activateCode,today);

        Assertions.assertEquals("romi@gmail.com",user1.getEmailUser().getEmail());
        Assertions.assertEquals("secret123",user1.getPassword());

        Assertions.assertEquals(LocalDateTime.now(fixedClock1),user1.getCreatedAt());
        Assertions.assertEquals(UserStatus.ACTIVE,user1.getStatus());
        Assertions.assertEquals(LocalDateTime.now(fixedClock1).plusDays(1), user1.getActivationExpiredAt());
    }

    @Test
    void testUser_Unsuccess() {

        Assertions.assertThrows(ValidationException.class,
                ()->User.createUserFactory("romi@gmail.com",null,LocalDateTime
                        .now(),null));
    }

}
