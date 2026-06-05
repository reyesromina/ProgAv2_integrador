package model;
import exception.ValidationException;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public class Order {
    private final Long id;
    private final User user;
    private OrderStatus status;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Order(Long id, User user, OrderStatus status, BigDecimal amount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }
//factory method
    public static Order create(User user, BigDecimal amount, Clock clock) {
    if (user == null) {
        throw new ValidationException("User cannot be null");
    }

    if (user.getStatus() != UserStatus.ACTIVE) {
        throw new ValidationException("User must be active");
    }

    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new ValidationException("Amount must be greater than zero");
    }

    LocalDateTime now = LocalDateTime.now(clock);
    return new Order(null, user, OrderStatus.PENDING, amount, now, now);
}
    public static Order restore(
            Long id,
            User user,
            OrderStatus status,
            BigDecimal amount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {

        return new Order(id, user, status, amount, createdAt, updatedAt);
    }

    private void changeStatus(OrderStatus newStatus, Clock clock) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now(clock);
    }

    public void process(Clock clock) {
        if (this.status != OrderStatus.PENDING) {
            throw new ValidationException("Order status must be PENDING");
        }
        changeStatus(OrderStatus.PROCESSING, clock);
    }

    public void  approve(Clock clock) {
        if(this.status != OrderStatus.PROCESSING){
            throw new ValidationException("Order status must be PROCESSING");
        }
        changeStatus(OrderStatus.APPROVED, clock);

    }
    public void  reject(Clock clock) {
        if(this.status != OrderStatus.PROCESSING){
            throw new ValidationException("Order status must be PROCESSING");
        }
        changeStatus(OrderStatus.REJECTED, clock);
    }
    public void  cancel(Clock clock) {
        if(this.status != OrderStatus.PENDING){
            throw new ValidationException("Order status must be PENDING");
        }
        changeStatus(OrderStatus.CANCELLED, clock);

    }
    public Long getId() { return id; }
    public User getUser() { return user; }
    public OrderStatus getStatus() { return status; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

}
