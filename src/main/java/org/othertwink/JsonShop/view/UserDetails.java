package org.othertwink.JsonShop.view;

import org.othertwink.JsonShop.model.entity.Order;
import org.othertwink.JsonShop.model.entity.User;
import org.othertwink.JsonShop.model.repository.OrderDAO;
import org.othertwink.JsonShop.model.repository.UserDAO;
import org.othertwink.JsonShop.view.DTO.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class UserDetails {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrderDAO orderDAO;

    public UserDetailsDTO getUserDetails(Long userId) {
        var optUser = userDAO.findById(userId);
        if (optUser.isEmpty()) {
            return null;
        }

        Optional<List<Order>> optOrders = orderDAO.findAllByUserId(userId);
        if (optOrders.isEmpty()) {
            return null;
        }

        User user = optUser.get();
        List<Order> orders = optOrders.get();

        BigDecimal totalCost = orders.stream()
                .map(Order::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new UserDetailsDTO(user, orders, totalCost);
    }
}
