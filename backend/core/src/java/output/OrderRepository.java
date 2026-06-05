package output;

import model.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> getOrders(Long user_id);
    Order save(Order order);
}
