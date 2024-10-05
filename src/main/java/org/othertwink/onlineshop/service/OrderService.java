package org.othertwink.onlineshop.service;

import org.othertwink.onlineshop.model.entity.Customer;
import org.othertwink.onlineshop.model.entity.Order;
import org.othertwink.onlineshop.model.entity.Product;

import java.util.List;

public interface OrderService {
    Order createOrder(Customer customer, List<Product> productList, String shippingAddress);
    Order deleteOrder(Long orderId);
    Order updateOrder(Order order);
    Order findById(Long orderId);
}
