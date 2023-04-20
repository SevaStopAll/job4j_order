package ru.job4j.order.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.order.domain.Order;
import ru.job4j.order.repository.OrderRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleOrderService implements OrderService {
    private final OrderRepository orders;

    @Override
    public Optional<Order> findById(int id) {
        return orders.findById(id);
    }

    @Override
    public Optional<Order> save(Order order) {
        return Optional.of(orders.save(order));
    }

    @Override
    public Collection<Order> findAll() {
        return orders.findAll();
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        if (orders.findById(id).isPresent()) {
            Order order = new Order();
            order.setId(id);
            this.orders.delete(order);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean update(Order order) {
        if (orders.findById(order.getId()).isPresent()) {
            this.orders.save(order);
            return true;
        }
        return false;
    }
}
