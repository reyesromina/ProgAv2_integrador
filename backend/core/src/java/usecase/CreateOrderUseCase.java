package usecase;

import exception.ValidationException;
import input.CreateOrderInput;
import model.Order;
import model.User;
import java.time.Clock;
import output.OrderRepository;
import output.UserRepository;

import java.math.BigDecimal;

public class CreateOrderUseCase implements CreateOrderInput {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final Clock clock;

    public CreateOrderUseCase(
            OrderRepository orderRepository,
            UserRepository userRepository,
            Clock clock
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.clock = clock;
    }
    @Override
    public Order createOrder(Long userId, BigDecimal amount) {

       User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ValidationException("User not found");
        }

        Order order = Order.create(
                user,
                amount,
                clock
        );

        Order savedOrder = orderRepository.save(order);

        if (savedOrder == null) {
            throw new ValidationException("Error saving order");
        }
        return savedOrder;
    }

}
