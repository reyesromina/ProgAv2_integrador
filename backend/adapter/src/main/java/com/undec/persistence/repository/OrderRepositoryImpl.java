package com.undec.persistence.repository;

import com.undec.persistence.crud.OrderRepositoryCrud;
import com.undec.persistence.entity.OrderData;
import com.undec.persistence.until.OrderMapper;
import model.Order;
import org.springframework.stereotype.Repository;
import output.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderRepositoryCrud crud;

    public OrderRepositoryImpl(OrderRepositoryCrud crud){
        this.crud = crud;
    }

    @Override
    public List<Order> getOrders(Long user_id) {
        List<OrderData> ordersData = crud.findByUserId(user_id);

        return ordersData.stream()
                .map(data -> OrderMapper.toDomain(data))
                .collect(Collectors.toList());
    }

    @Override
    public Order save(Order order) {
        OrderData dataToSave = OrderMapper.toData(order);

        OrderData savedData = crud.save(dataToSave);

        return OrderMapper.toDomain(savedData);
    }
}