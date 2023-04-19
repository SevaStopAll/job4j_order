package ru.job4j.order.service;

import ru.job4j.order.Domain.Product;

import java.util.Collection;
import java.util.Optional;

public interface ProductService {
    Optional<Product> findByName(String productName);

    Optional<Product> findById(int productId);

    Optional<Product> save(Product product);

    Collection<Product> findAll();
}
