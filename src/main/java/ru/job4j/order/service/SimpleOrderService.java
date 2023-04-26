package ru.job4j.order.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.order.domain.Order;
import ru.job4j.order.domain.Dish;
import ru.job4j.order.domain.Status;
import ru.job4j.order.repository.CustomerRepository;
import ru.job4j.order.repository.OrderRepository;
import ru.job4j.order.repository.StatusRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SimpleOrderService implements OrderService {
    private final OrderRepository orders;
    private final StatusRepository statuses;

    private final CustomerRepository customers;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Override
    public Optional<Order> save(Order order) {
        var savedOrder = orders.save(order);
        Map data = new HashMap();
        data.put("id", order.getId());
        data.put("customer", order.getCustomer().getName());
        data.put("dishes", order.getDishes().stream().map(dish -> dish.getId()).collect(Collectors.toList()));
        data.put("time", LocalDateTime.now());
        data.put("status", 1);
        kafkaTemplate.send("job4j_preorder", data);
        String description = "Уважаемый клиент, Ваш заказ создан";
        kafkaTemplate.send("job4j_messengers", description);
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
            orders.save(order);
            String notification = "Уважаемый клиент, статус Ваш заказ " + order.getStatus().getName();
            kafkaTemplate.send("job4j_messengers", notification);
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> getDishesIds(Optional<Order> order) {
        return order.get().getDishes().stream().mapToInt(Dish::getId).boxed().toList();
    }

    public Optional<Status> findStatusById(int id) {
        return statuses.findById(id);
    }

    @KafkaListener(topics = "cooked_order")
    public void receiveStatus(Map<String, Integer> data) {
        Order order = new Order();
        int id = data.get("id");
        order.setId(id);
        order.setStatus(findStatusById(data.get("status")).get());
        order.setCustomer(customers.findByName(orders.findById(id).get().getCustomer().getName()).get());
        log.debug(String.valueOf(data.get("id")));
        log.debug(String.valueOf(data.get("status")));
        update(order);
    }
}
