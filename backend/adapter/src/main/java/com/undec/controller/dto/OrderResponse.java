package com.undec.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("statusOrder")
    private String status;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    public OrderResponse() {}

    public OrderResponse(Long id, Long userId, String status, BigDecimal amount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static OrderResponse fromDomainOrder(Order order) {
        if (order == null) return null;

        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getStatus().name(),
                order.getAmount(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
