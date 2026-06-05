package com.undec.persistence.crud;

import com.undec.persistence.entity.OrderData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepositoryCrud extends CrudRepository<OrderData, Long> {
    List<OrderData> findByUserId (Long userId);

}
