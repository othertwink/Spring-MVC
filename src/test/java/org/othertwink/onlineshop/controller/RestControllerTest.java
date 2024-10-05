package org.othertwink.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.othertwink.onlineshop.model.entity.Customer;
import org.othertwink.onlineshop.model.entity.Order;
import org.othertwink.onlineshop.model.entity.Product;
import org.othertwink.onlineshop.model.entity.enums.OrderStatus;
import org.othertwink.onlineshop.service.OrderService;
import org.othertwink.onlineshop.service.ProductService;
import org.othertwink.onlineshop.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @MockBean
    ProductServiceImpl productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenValidProduct_thenReturnsCreated() throws Exception {
        Product validProduct = Product.builder()
                .productId(1L)
                .name("Valid Product")
                .description("This is a valid product")
                .price(BigDecimal.valueOf(100.00))
                .quantityInStock(10)
                .build();

        String productJson = objectMapper.writeValueAsString(validProduct);

        mockMvc.perform(post("/shop/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated());
    }

    @Test
    void whenInvalidProductRequest_thenReturnsBadRequest() throws Exception {
        Product invalidProduct = new Product();
        invalidProduct.setName(null); // Не заполнено обязательное поле

        String invalidJson = objectMapper.writeValueAsString(invalidProduct);

        mockMvc.perform(post("/shop/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenValidOrderRequest_thenReturnsCreated() throws Exception {
        Customer validCustomer = new Customer();
        validCustomer.setCustomerId(1L);
        validCustomer.setFirstName("Name");
        validCustomer.setLastName("Surname");
        validCustomer.setEmail("common@rumail.ru");
        validCustomer.setContactNumber("+7(999)123-22-33");

        Product validProduct = new Product();
        validProduct.setProductId(1L);
        validProduct.setName("Product Name");
        validProduct.setDescription("This is a product");
        validProduct.setPrice(BigDecimal.valueOf(50.00));
        validProduct.setQuantityInStock(5);

        List<Product> productList = new ArrayList<>();
        productList.add(validProduct);

        Order validOrder = new Order();
        validOrder.setOrderId(1L);
        validOrder.setCustomer(validCustomer);
        validOrder.setProducts(productList);
        validOrder.setShippingAddress("Shipping Address");
        validOrder.setTotalPrice(BigDecimal.valueOf(50.00));
        validOrder.setOrderStatus(OrderStatus.PENDING);

        when(orderService.createOrder(any(Customer.class), anyList(), anyString())).thenReturn(validOrder);

        mockMvc.perform(post("/shop/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrder)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenInvalidOrderRequest_thenReturnsBadRequest() throws Exception {
        Order invalidOrder = new Order();
        invalidOrder.setCustomer(null);

        String invalidJson = objectMapper.writeValueAsString(invalidOrder);

        mockMvc.perform(post("/shop/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetExistingProduct_thenReturnsOk() throws Exception {
        Product existingProduct = new Product();
        existingProduct.setProductId(1L);
        existingProduct.setName("Existing Product");
        existingProduct.setDescription("This is an existing product");
        existingProduct.setPrice(BigDecimal.valueOf(150.00));
        existingProduct.setQuantityInStock(5);


        when(productService.findById(existingProduct.getProductId())).thenReturn(existingProduct);

        mockMvc.perform(get("/shop/products/{id}", existingProduct.getProductId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Existing Product"));
    }

    @Test
    void whenGetNonExistingProduct_thenReturnsNotFound() throws Exception {
        Long productId = 999L;

        when(productService.findById(productId)).thenThrow(new IllegalArgumentException("Product not found"));

        mockMvc.perform(get("/shop/products/{productId}", productId))
                .andExpect(status().isBadRequest());
    }


}
