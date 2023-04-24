package ru.job4j.order.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.order.domain.Order;
import ru.job4j.order.domain.Dish;
import ru.job4j.order.repository.OrderRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimpleOrderService implements OrderService {
    private final OrderRepository orders;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Override
    public Optional<Order> save(Order order) {
        var savedOrder = orders.save(order);
        Map data = new HashMap();
        data.put("id", order.getId());
        data.put("customer", order.getCustomer().getName());
        data.put("dishes", order.getDishes().stream().map(dish -> dish.getId()).collect(Collectors.toList()));
        kafkaTemplate.send("job4j_orders", data);
        return Optional.of(savedOrder);
    }

    @Override
    public Optional<Order> findById(int id) {
        return orders.findById(id);
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

    @Override
    public List<Integer> getDishesIds(Optional<Order> order) {
        return order.get().getDishes().stream().mapToInt(Dish::getId).boxed().toList();
    }
}
