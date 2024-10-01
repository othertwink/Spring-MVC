package org.othertwink.JsonShop.model.repository;

import org.othertwink.JsonShop.model.entity.Order;
import org.othertwink.JsonShop.model.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);
    Optional<Order> findByStatus(OrderStatus orderStatus);
    Optional<List<Order>> findAllByUserId(Long userId);
}
