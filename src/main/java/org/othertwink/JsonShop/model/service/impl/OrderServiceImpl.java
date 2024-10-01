package org.othertwink.JsonShop.model.service.impl;

import org.othertwink.JsonShop.model.entity.Order;
import org.othertwink.JsonShop.model.repository.OrderDAO;
import org.othertwink.JsonShop.model.service.OrderService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static OrderDAO orderDAO;
     public OrderServiceImpl (OrderDAO orderDAO) {
         OrderServiceImpl.orderDAO = orderDAO;
     }

    @Override
    @Transactional
    public Order createOrder(Long id, List<String> products, BigDecimal cost, Long userId) {
        try {
            var optional = orderDAO.findById(id);
            if (optional.isPresent()) {
                throw new IllegalArgumentException("Order with id " + id + " already exists");
            }

            Order transientOrder = Order.builder()
                    .products(products)
                    .cost(cost)
                    .userId(userId)
                    .build();

            return orderDAO.save(transientOrder);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Could not create order (integrity violation)", e);
        }
    }

    @Override
    @Transactional
    public Order deleteOrder(Long id)  {
        Optional<Order> optional = orderDAO.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Order with id " + id + " not found");
        }

        Order persistentOrder = optional.get();
        orderDAO.delete(persistentOrder);
        return persistentOrder;
    }

    @Override // больше контроля чем напрямую к dao
    public List<Order> findAllOrders() {
        return orderDAO.findAll();
    }

    @Override // больше контроля чем напрямую к dao
    public Optional<Order> findOrderById(Long id) {
        return orderDAO.findById(id);
    }

}
