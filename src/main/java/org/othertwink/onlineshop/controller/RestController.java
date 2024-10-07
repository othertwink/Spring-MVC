package org.othertwink.onlineshop.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.othertwink.onlineshop.exception.util.ValidationUtil;
import org.othertwink.onlineshop.model.entity.Order;
import org.othertwink.onlineshop.model.entity.Product;
import org.othertwink.onlineshop.service.OrderService;
import org.othertwink.onlineshop.service.ProductService;
import org.othertwink.onlineshop.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/shop")
public class RestController {

    private final ValidationUtil validationUtil;

    private final ProductService productService;

    private final OrderService orderService;

    private final ObjectMapper objectMapper;

    public RestController(ValidationUtil validationUtil,
                          ProductService productService,
                          OrderService orderService,
                          ObjectMapper objectMapper) {
        this.validationUtil = validationUtil;
        this.productService = productService;
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/products/catalog")
    public ResponseEntity<Page<Product>> getAllProducts(
            @PageableDefault(size = 10, sort = "title") Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<String> getProduct(@PathVariable Long productId) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(productService.findById(productId)));
    }

    @PostMapping(value = "/products/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.Create.class)
    public ResponseEntity<String> createProduct(@RequestBody @JsonView(View.Create.class) String productJson) throws IOException {
        Product product = objectMapper.readerWithView(View.Create.class).readValue(productJson, Product.class);
        validationUtil.validate(product);
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(createdProduct));
    }

    @PutMapping(value = "/products/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProduct(String jsonBody) throws JsonProcessingException {
        Product product = productService.updateProduct(objectMapper.readValue(jsonBody, Product.class));
        validationUtil.validate(product);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(product));
    }

    @DeleteMapping(value = "/products/{productId}/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProduct(@PathVariable Long productId) throws JsonProcessingException {
        Product product = productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(product));
    }

    @PostMapping(value = "/orders/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrder(@RequestBody String orderJson) throws JsonProcessingException {
        Order order = objectMapper.readValue(orderJson, Order.class);
        validationUtil.validate(order);
        order.getProducts().forEach(validationUtil::validate);
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(createdOrder));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<String> getOrder(@PathVariable Long orderId) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(orderService.findById(orderId)));
    }

}
