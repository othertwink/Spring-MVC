package org.othertwink.JsonShop.model.service;

import org.othertwink.JsonShop.model.entity.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    // почему @Transactional не пикается отсюда в имплементацию?
    Order createOrder(Long id, List<String> products, BigDecimal cost, Long userId);

    Order deleteOrder(Long id);

    List<Order> findAllOrders();

    Optional<Order> findOrderById(Long id);
}
