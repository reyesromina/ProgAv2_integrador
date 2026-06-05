package usecase;

import exception.ValidationException;
import model.Order;
import model.OrderStatus;
import model.User;
import model.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import output.OrderRepository;
import output.UserRepository;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user; // mockeamos user porque solo necesitamos el status

    private Clock fixedClock() {
        return Clock.fixed(
                Instant.parse("2024-01-01T10:00:00Z"),
                ZoneId.systemDefault()
        );
    }

    @Test
    void createOrder_UserNotFound_ThrowsException() {

        Clock clock = fixedClock();

        when(userRepository.findUserById(1L)).thenReturn(null);

        CreateOrderUseCase useCase =
                new CreateOrderUseCase(orderRepository, userRepository, clock);

        Assertions.assertThrows(
                ValidationException.class,
                () -> useCase.createOrder(1L, new BigDecimal("100"))
        );

        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_UserNotAccepted_ThrowsException() {

        Clock clock = fixedClock();

        when(userRepository.findUserById(1L)).thenReturn(user);
        when(user.getStatus()).thenReturn(UserStatus.PENDING);

        CreateOrderUseCase useCase =
                new CreateOrderUseCase(orderRepository, userRepository, clock);

        Assertions.assertThrows(
                ValidationException.class,
                () -> useCase.createOrder(1L, new BigDecimal("100"))
        );

        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_InvalidAmount_ThrowsException() {

        Clock clock = fixedClock();

        when(userRepository.findUserById(1L)).thenReturn(user);
        when(user.getStatus()).thenReturn(UserStatus.ACTIVE);

        CreateOrderUseCase useCase =
                new CreateOrderUseCase(orderRepository, userRepository, clock);

        Assertions.assertThrows(
                ValidationException.class,
                () -> useCase.createOrder(1L, BigDecimal.ZERO)
        );

        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_SaveFails_ThrowsException() {

        Clock clock = fixedClock();

        when(userRepository.findUserById(1L)).thenReturn(user);
        when(user.getStatus()).thenReturn(UserStatus.ACTIVE);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(null); // simulamos fallo de persistencia

        CreateOrderUseCase useCase =
                new CreateOrderUseCase(orderRepository, userRepository, clock);

        Assertions.assertThrows(
                ValidationException.class,
                () -> useCase.createOrder(1L, new BigDecimal("100"))
        );
    }

    @Test
    void createOrder_ValidOrder_Success() {

        Clock clock = fixedClock();

        when(userRepository.findUserById(1L)).thenReturn(user);
        when(user.getStatus()).thenReturn(UserStatus.ACTIVE);

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateOrderUseCase useCase =
                new CreateOrderUseCase(orderRepository, userRepository, clock);

        Order result =
                useCase.createOrder(1L, new BigDecimal("100"));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(OrderStatus.PENDING, result.getStatus());

        verify(orderRepository).save(any(Order.class));
    }
}