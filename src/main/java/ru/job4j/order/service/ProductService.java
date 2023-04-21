package ru.job4j.order.service;

import ru.job4j.order.domain.Dish;

import java.util.Collection;
import java.util.Optional;

public interface ProductService {
    Optional<Dish> findByName(String productName);

    Optional<Dish> findById(int productId);

    Optional<Dish> save(Dish dish);

    Collection<Dish> findAll();
}
