package ru.job4j.order.service;

import ru.job4j.order.domain.Order;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> findById(int id);

    Optional<Order> save(Order order);

    Collection<Order> findAll();

    boolean delete(int id);

    boolean update(Order order);

    List<Integer> getDishesIds(Optional<Order> order);
}
