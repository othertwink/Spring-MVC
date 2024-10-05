package org.othertwink.onlineshop.service;

import org.othertwink.onlineshop.model.entity.Order;
import org.othertwink.onlineshop.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductService {
    Product createProduct(String name, String description, BigDecimal price, Integer quantityInStock);

    Page<Product> getAllProducts(Pageable pageable);

    Product deleteProduct(Long id);
    Product updateProduct(Product product);
    Product findById(Long orderId);
}
