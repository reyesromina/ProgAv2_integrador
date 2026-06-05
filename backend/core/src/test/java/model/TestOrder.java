package model;

import exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.*;
import java.util.UUID;

public class TestOrder {
    private Clock fixedClock() {
        // para devolver siempre la misma fecha
        return Clock.fixed(
                LocalDateTime.of(2026, 1, 1, 10, 0)
                        .atZone(ZoneId.systemDefault())
                        .toInstant(),
                ZoneId.systemDefault()
        );
    }

    @Test
    void testOrder_Success() {

        Clock clock = fixedClock();
        String code = UUID.randomUUID().toString();

        // IMPORTANTE: usar fecha real para evitar expiración
        LocalDateTime today= LocalDateTime.of(2024,1,1,2,0);
        LocalDateTime todayCreate= LocalDateTime.of(2024,1,1,0,0);
        User user1 = User.createUserFactory("ROMi@gmail.com","secret123",
               todayCreate,code);

        user1.activate(code,today);

        Order order = Order.create(user1, new BigDecimal("200"), clock);
        Assertions.assertNotNull(order);
    }


    @Test
    void instanceOrder_AllAttributes_InstanceCorrect() {

        Clock clock = fixedClock();
        String code = UUID.randomUUID().toString();

        LocalDateTime today= LocalDateTime.of(2024,1,1,2,0);
        LocalDateTime todayCreate= LocalDateTime.of(2024,1,1,0,0);
        User user1 = User.createUserFactory("ROMi@gmail.com","secret123",
                todayCreate,code);
        user1.activate(code,today);
        Order order = Order.create(user1, new BigDecimal("200"), clock);

        Assertions.assertEquals(user1, order.getUser());
        Assertions.assertEquals(OrderStatus.PENDING, order.getStatus());
        Assertions.assertEquals(new BigDecimal("200"), order.getAmount());

        // fecha controlada por clock
        LocalDateTime expectedTime = LocalDateTime.now(clock);
        Assertions.assertEquals(expectedTime, order.getCreatedAt());

        // updatedAt puede o no ser null según implementación
        if (order.getUpdatedAt() != null) {
            Assertions.assertTrue(
                    !order.getUpdatedAt().isBefore(order.getCreatedAt())
            );
        }
    }

    @Test
    void instanceOrder_UserNotAccepted_InstanceNotCorrect() {
        // No se puede crear una orden si el usuario no está ACCEPTED
        Clock clock = fixedClock();
        String code = UUID.randomUUID().toString();

        LocalDateTime today= LocalDateTime.of(2024,1,2,2,0);

        LocalDateTime todayCreate= LocalDateTime.of(2024,1,1,0,0);
        User user1 = User.createUserFactory("ROMi@gmail.com","secret123",
                todayCreate,code);



        Assertions.assertThrows(ValidationException.class, () -> { user1.activate(code,today);});
        Assertions.assertThrows(ValidationException.class,
                () -> Order.create(user1, new BigDecimal("100"), clock));
    }

    @Test
    void instanceOrder_AmountNull_InstanceNotCorrect() {
        // Que el monto no pueda ser null.
        Clock clock = fixedClock();
        String code = UUID.randomUUID().toString();

        LocalDateTime today= LocalDateTime.of(2024,1,1,2,0);
        LocalDateTime todayCreate= LocalDateTime.of(2024,1,1,0,0);
        User user1 = User.createUserFactory("ROMi@gmail.com","secret123",
                todayCreate,code);

        user1.activate(code,today);

        Assertions.assertThrows(ValidationException.class,
                () -> Order.create(user1, null, clock));
    }

    @Test
    void instanceOrder_AmountLessOrEqualZero_InstanceNotCorrect() {
        // Que el monto sea mayor a cero.
        Clock clock = fixedClock();
        String code = UUID.randomUUID().toString();

        LocalDateTime today= LocalDateTime.of(2024,1,1,2,0);
        LocalDateTime todayCreate= LocalDateTime.of(2024,1,1,0,0);
        User user1 = User.createUserFactory("ROMi@gmail.com","secret123",
                todayCreate,code);

        user1.activate(code,today);

        Assertions.assertThrows(ValidationException.class,
                () -> Order.create(user1, BigDecimal.ZERO, clock));
    }

    @Test
    void instanceOrder_FlujoNormal_ProcessingToApproved() {

        Clock clock = fixedClock();
        String code = UUID.randomUUID().toString();

        LocalDateTime today= LocalDateTime.of(2024,1,1,2,0);
        LocalDateTime todayCreate= LocalDateTime.of(2024,1,1,0,0);

        User user1 = User.createUserFactory(
                "ROMi@gmail.com",
                "secret123",
                todayCreate,
                code
        );

        user1.activate(code,today);

        Order order = Order.create(user1, new BigDecimal("150"), clock);

        // CAMBIO CLAVE
        order.process(clock);
        order.approve(clock);

        Assertions.assertEquals(OrderStatus.APPROVED, order.getStatus());
    }

    @Test
    void instanceOrder_FlujoAlternative_ProcessingToRejected() {
        // Valida caminos válidos desde PROCESSING: APPROVED o REJECTED
        Clock clock = fixedClock();
        String code = UUID.randomUUID().toString();

        LocalDateTime today= LocalDateTime.of(2024,1,1,2,0);
        LocalDateTime todayCreate= LocalDateTime.of(2024,1,1,0,0);
        User user1 = User.createUserFactory("ROMi@gmail.com","secret123",
                todayCreate,code);


        user1.activate(code,today);

        Order order = Order.create(user1, new BigDecimal("150"), clock);

        order.process(clock);
        order.reject(clock);

        Assertions.assertEquals(OrderStatus.REJECTED, order.getStatus());
        Assertions.assertTrue(order.getUpdatedAt().isAfter(order.getCreatedAt())
                || order.getUpdatedAt().isEqual(order.getCreatedAt()));
    }

    @Test
    void instanceOrder_FlujoAlternative_CancelFromPending() {
        // Valida PENDING → CANCELLED (solo desde pending)
        Clock clock = fixedClock();
        String code = UUID.randomUUID().toString();

        LocalDateTime today= LocalDateTime.of(2024,1,1,2,0);
        LocalDateTime todayCreate= LocalDateTime.of(2024,1,1,0,0);
        User user1 = User.createUserFactory("ROMi@gmail.com","secret123",
                todayCreate,code);

        user1.activate(code,today);

        Order order = Order.create(user1, new BigDecimal("150"), clock);

        order.cancel(clock);

        Assertions.assertEquals(OrderStatus.CANCELLED, order.getStatus());
        Assertions.assertTrue(order.getUpdatedAt().isAfter(order.getCreatedAt())
                || order.getUpdatedAt().isEqual(order.getCreatedAt()));
    }

    @Test
    void instanceOrder_InvalidTransition_ApproveFromPending() {
        // Valida que no se pueda hacer PENDING → APPROVED sin pasar por PROCESSING
        Clock clock = fixedClock();
        String code = UUID.randomUUID().toString();

        LocalDateTime today= LocalDateTime.of(2024,1,1,2,0);
        LocalDateTime todayCreate= LocalDateTime.of(2024,1,1,0,0);
        User user1 = User.createUserFactory("ROMi@gmail.com","secret123",
                todayCreate,code);

        user1.activate(code,today);

        Order order = Order.create(user1, new BigDecimal("150"), clock);

        Assertions.assertThrows(ValidationException.class, ()->
                order.approve(clock));
    }
}
