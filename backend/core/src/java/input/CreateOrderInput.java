package input;

import model.Order;

import java.math.BigDecimal;

public interface CreateOrderInput {
    Order createOrder(Long userId, BigDecimal ammount);
}
