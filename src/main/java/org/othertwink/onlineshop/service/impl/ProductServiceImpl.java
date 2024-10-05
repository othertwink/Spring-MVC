package org.othertwink.onlineshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.othertwink.onlineshop.model.entity.Product;
import org.othertwink.onlineshop.repository.ProductRepo;
import org.othertwink.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    @Transactional
    public Product createProduct(String name, String description, BigDecimal price, Integer quantityInStock) {
        Product order = Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .quantityInStock(quantityInStock)
                .build();
        return productRepo.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {
        Product existingProduct = findById(product.getProductId());
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantityInStock(product.getQuantityInStock());
        return productRepo.save(existingProduct);
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    @Transactional
    public Product deleteProduct(Long id) {
        Product product = findById(id);
        productRepo.delete(product);
        return product;
    }
}
