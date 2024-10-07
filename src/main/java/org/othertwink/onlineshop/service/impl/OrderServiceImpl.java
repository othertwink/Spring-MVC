package org.othertwink.onlineshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.othertwink.onlineshop.model.entity.Customer;
import org.othertwink.onlineshop.model.entity.Order;
import org.othertwink.onlineshop.model.entity.Product;
import org.othertwink.onlineshop.model.entity.enums.OrderStatus;
import org.othertwink.onlineshop.repository.OrderRepo;
import org.othertwink.onlineshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;

    public OrderServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    @Transactional
    public Order createOrder(Customer customer, List<Product> productList, String shippingAddress) {
        BigDecimal totalPrice = productList.stream()
                .map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order newOrder = Order.builder()
                .customer(customer)
                .products(productList)
                .shippingAddress(shippingAddress)
                .totalPrice(totalPrice)
                .orderStatus(OrderStatus.CREATED)
                .build();
        return orderRepo.save(newOrder);
    }


    @Override
    @Transactional
    public Order createOrder(Order order) {
        BigDecimal totalPrice = order.getProducts().stream()
                .map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order newOrder = Order.builder()
                .customer(order.getCustomer())
                .products(order.getProducts())
                .shippingAddress(order.getShippingAddress())
                .totalPrice(totalPrice)
                .orderStatus(OrderStatus.CREATED)
                .build();
        return orderRepo.save(newOrder);
    }

    @Override
    @Transactional
    public Order deleteOrder(Long orderId) {
        Order order = findById(orderId);
        orderRepo.delete(order);
        return order;
    }

    @Override
    @Transactional
    public Order updateOrder(Order order) {
        Order existingOrder = findById(order.getOrderId());
        existingOrder.setProducts(order.getProducts());
        existingOrder.setShippingAddress(order.getShippingAddress());
        existingOrder.setTotalPrice(order.getTotalPrice());
        existingOrder.setOrderStatus(order.getOrderStatus());
        return orderRepo.save(existingOrder);
    }

    @Override
    @Transactional
    public Order findById(Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("No order under Id " + orderId + " found"));
    }
}
