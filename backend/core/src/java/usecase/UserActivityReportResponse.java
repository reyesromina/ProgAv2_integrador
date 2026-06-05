package usecase;

import model.Order;
import model.OrderStatus;
import model.UserStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class UserActivityReportResponse {


    private final LocalDateTime reportAt;
    private final Long userId;
    private final String emailUser;
    private final UserStatus userStatus;
    private final LocalDateTime createdUserAt;
    private final int TotalCantOrder;
    private final BigDecimal TotalAmount;
    private final List<OrderData> Orders;

    public UserActivityReportResponse(
                                      LocalDateTime reportAt,
                                      Long userId,
                                      String emailUser,
                                      UserStatus userStatus,
                                      LocalDateTime createdUserAt,
                                      int totalCantOrder,
                                      BigDecimal totalAmount,
                                      List<OrderData> orders) {

        this.reportAt = reportAt;
        this.userId = userId;
        this.emailUser = emailUser;
        this.userStatus = userStatus;
        this.createdUserAt = createdUserAt;
        TotalCantOrder = totalCantOrder;
        TotalAmount = totalAmount;
        Orders = orders;
    }



    public LocalDateTime getReportAt() {
        return reportAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public LocalDateTime getCreatedUserAt() {
        return createdUserAt;
    }

    public int getTotalCantOrder() {
        return TotalCantOrder;
    }

    public BigDecimal getTotalAmount() {
        return TotalAmount;
    }

    public List<OrderData> getOrders() {
        return Orders;
    }
    public static class OrderData {
        private final Long orderId;
        private final LocalDateTime createdOrderAt;
        private final BigDecimal amount;
        private final OrderStatus status;

        public OrderData(Long orderId,
                         LocalDateTime createdOrderAt,
                         BigDecimal amount,
                         OrderStatus status) {
            this.orderId = orderId;
            this.createdOrderAt = createdOrderAt;
            this.amount = amount;
            this.status = status;
        }

        public Long getOrderId() {
            return orderId;
        }
        public LocalDateTime getCreatedOrderAt() {
            return createdOrderAt;
        }
        public BigDecimal getAmount() {
            return amount;
        }
        public OrderStatus getStatus() {
            return status;
        }
    }
}

