package ru.job4j.order.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.order.domain.Dish;
import ru.job4j.order.domain.Order;
import ru.job4j.order.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orders;
    @Value("http://localhost:8080/dishes/")
    private String url;

    private final RestTemplate client;

    @GetMapping("/")
    public List<Order> findAll() {
        return (List<Order>) this.orders.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable int id) {
        var order = this.orders.findById(id);
        if (order.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "This order is not found. Please, check id one more time."
            );
        }
        Order result = order.get();
        List<Integer> ids = orders.getDishesIds(order);
        List<Dish> dishes = new ArrayList<>();
        for (int i : ids) {
            Dish dish = client.getForEntity(
                    String.format("%s/%s", url, i),
                    Dish.class
            ).getBody();
            dishes.add(dish);
        }
        order.get().setDishes(dishes);
        return new ResponseEntity<>(result,
                HttpStatus.OK
                );
    }

    @PostMapping("/")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        return new ResponseEntity<>(
                this.orders.save(order).get(),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Order order) {
        if (orders.update(order)) {
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (orders.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
