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

    private final UserService userService;
    private final OrderService orderService;
    private final UserSummary userSummaryView;
    private final UserDetails userDetailsView;

    public RestController(UserService userService,
                          OrderService orderService,
                          UserSummary userSummaryView,
                          UserDetails userDetailsView) {
        this.userService = userService;
        this.orderService = orderService;
        this.userSummaryView = userSummaryView;
        this.userDetailsView = userDetailsView;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order.getId(), order.getProducts(), order.getCost(), order.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping
    @JsonView(UserSummary.class)
    public ResponseEntity<List<User>> getUserSummary() {
        List<User> users = userSummaryView.getUserSummary();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/details")
    @JsonView(UserDetails.class)
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable Long id) {
        UserDetailsDTO userDetails = userDetailsView.getUserDetails(id);
        return ResponseEntity.ok(userDetails);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
            User createdUser = userService.createUser(user.getName(), user.getEmail(), user.getAddress());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user.getName(), user.getEmail());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
