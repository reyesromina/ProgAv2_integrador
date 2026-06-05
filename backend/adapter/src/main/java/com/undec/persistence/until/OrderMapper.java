package com.undec.persistence.until;

import com.undec.persistence.entity.OrderData;
import model.Order;
import model.OrderStatus;

public class OrderMapper {
    public static Order toDomain(OrderData entity) {
        if (entity == null) {
            return null;
        }

        return Order.restore(
                entity.getId(),
                UserMapper.mapToUserDomain(entity.getUser()),
                OrderStatus.valueOf(entity.getStatus()),
                entity.getAmount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static OrderData toData(Order domain) {
        if (domain == null) {
            return null;
        }

        return new OrderData(
                domain.getId(),
                UserMapper.mapToUserData(domain.getUser()),
                domain.getStatus().name(),
                domain.getAmount(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }
}
