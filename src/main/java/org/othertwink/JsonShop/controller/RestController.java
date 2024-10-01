package org.othertwink.JsonShop.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.othertwink.JsonShop.model.entity.Order;
import org.othertwink.JsonShop.model.entity.User;
import org.othertwink.JsonShop.model.service.OrderService;
import org.othertwink.JsonShop.model.service.UserService;
import org.othertwink.JsonShop.view.DTO.UserDetailsDTO;
import org.othertwink.JsonShop.view.UserDetails;
import org.othertwink.JsonShop.view.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class RestController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserSummary userSummaryView;

    @Autowired
    private UserDetails userDetailsView;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        try {
            Order createdOrder = orderService.createOrder(order.getId(), order.getProducts(), order.getCost(), order.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    @JsonView(UserSummary.class)
    public ResponseEntity<List<User>> getUserSummary() {
        List<User> users = userSummaryView.getUserSummary();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/details")
    @JsonView(UserDetails.class)
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable Long id) {
        UserDetailsDTO userDetails = userDetailsView.getUserDetails(id);
        if (userDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDetails);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user.getName(), user.getEmail(), user.getAddress());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user.getName(), user.getEmail());
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
