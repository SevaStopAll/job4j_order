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
import java.util.stream.IntStream;

@Service
@Slf4j
@AllArgsConstructor
public class SimpleOrderService implements OrderService {
    private final OrderRepository orders;
    private final StatusRepository statuses;
    private final CustomerRepository customers;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    /**
     * Создать новый заказ.
     *
     * @param order заказ.
     * @return Optional заказа.
     */
    @Override
    public Optional<Order> save(Order order) {
        System.out.println(order.getMethod());

        var totalPrice = order.getDishes().stream().flatMapToInt(dish -> IntStream.of(dish.getPrice())).reduce((d1, d2) -> d1+ d2).getAsInt();
        order.setPrice(totalPrice);
        var savedOrder = orders.save(order);
        Map data = new HashMap();
        data.put("id", order.getId());
        data.put("customer", order.getCustomer().getName());
        data.put("address", order.getCustomer().getAddress());
        data.put("dishes", order.getDishes().stream().map(dish -> dish.getId()).collect(Collectors.toList()));
        data.put("time", LocalDateTime.now());
        data.put("price", order.getPrice());
        data.put("payment_method", order.getMethod().getName());
        data.put("status", 1);
        kafkaTemplate.send("job4j_preorder", data);
        String description = "Уважаемый клиент, Ваш заказ создан";
        kafkaTemplate.send("job4j_messengers", description);
        return Optional.of(savedOrder);
    }

    /**
     * Найти заказ по идентификатору.
     *
     * @param id имя заказа.
     * @return Optional заказа.
     */
    @Override
    public Optional<Order> findById(int id) {
        return orders.findById(id);
    }

    /**
     * Вывести все заказы.
     *
     * @return Список всех статусов.
     */
    @Override
    public Collection<Order> findAll() {
        return orders.findAll();
    }

    /**
     * Удалить заказ по идентификатору.
     *
     * @param id имя заказа.
     * @return результат удаления.
     */
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

    /**
     * Обновить заказ и отправить уведомление пользователю.
     *
     * @param order заказ, на который обновляем.
     * @return результат обновления.
     */
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

    /**
     * Вывести список идентификаторов блюд, входящих в заказ.
     *
     * @param order заказ, в котором находим блюда.
     * @return список идентификаторов блюд.
     */
    @Override
    public List<Integer> getDishesIds(Optional<Order> order) {
        return order.get().getDishes().stream().mapToInt(Dish::getId).boxed().toList();
    }

    /**
     * Найти статус по его id.
     *
     * @param id статус заказ.
     * @return Optional статуса.
     */
    public Optional<Status> findStatusById(int id) {
        return statuses.findById(id);
    }

    /**
     * Получить с кухни данные по заказу.
     *
     * @param data данные по заказу.
     */
    @KafkaListener(topics = "cooked_order")
    public void receiveCookingStatus(Map data) {
        var order = orders.findById((int) data.get("id"));
        if (order.isEmpty()) {
            throw new IllegalArgumentException();
        }
        var updatedOrder = order.get();
        updatedOrder.setStatus(findStatusById((Integer) data.get("status")).get());
        log.debug(String.valueOf(data.get("id")));
        log.debug(String.valueOf(data.get("status")));
        update(updatedOrder);
        if (data.get("status").equals(2)) {
            sendToDelivery(updatedOrder.getId());
        }
    }

    /**
     * Отправить службе доставки данные по заказу(идентификатор и статус).
     *
     * @param id идентификатор заказа.
     */
    public void sendToDelivery(int id) {
        var order = orders.findById(id);
        if (order.isEmpty()) {
            throw new IllegalArgumentException();
        }
        var orderToDeliver = order.get();
        Map data = new HashMap();
        data.put("address", orderToDeliver.getCustomer().getAddress());
        data.put("price", orderToDeliver.getPrice());
        data.put("payment_method", orderToDeliver.getMethod().getName());
        kafkaTemplate.send("delivery_service", data);
    }

    /**
     * Получить от службы доставки статус по заказу.
     *
     * @param data данные по заказу (идентификатор и статус).
     */
    @KafkaListener(topics = "delivered_order")
    public void recieveDeliveryStatus(Map<String, Integer> data) {
        var order = orders.findById((int) data.get("id"));
        if (order.isEmpty()) {
            throw new IllegalArgumentException();
        }
        var updatedOrder = order.get();
        updatedOrder.setStatus(findStatusById((Integer) data.get("status")).get());
        log.debug(String.valueOf(data.get("id")));
        log.debug(String.valueOf(data.get("status")));
        update(updatedOrder);
        if (data.get("status").equals(2)) {
        sendToPayment(updatedOrder.getId());
        }
    }

    /**
     * Отправить сервису оплаты данные по заказу(идентификатор и статус).
     *
     * @param id идентификатор заказа.
     */
    public void sendToPayment(int id) {
        var order = orders.findById(id);
        if (order.isEmpty()) {
            throw new IllegalArgumentException();
        }
        var orderToPay = order.get();
        Map data = new HashMap();
        data.put("time", LocalDateTime.now());
        data.put("price", orderToPay.getPrice());
        data.put("payment_method", orderToPay.getMethod().getId());
        kafkaTemplate.send("payment_service", data);
    }

    /**
     * Получить от службы оплаты статус по заказу.
     *
     * @param data данные по заказу (идентификатор и статус).
     */
    @KafkaListener(topics = "paid_order")
    public void recievePaymentStatus(Map<String, Integer> data) {
        var order = orders.findById((int) data.get("id"));
        if (order.isEmpty()) {
            throw new IllegalArgumentException();
        }
        var updatedOrder = order.get();
        int status = data.get("status");
        if (status == 2) {
            updatedOrder.setStatus(findStatusById(4).get());
        }
        updatedOrder.setStatus(findStatusById(5).get());
        log.debug(String.valueOf(data.get("id")));
        log.debug(String.valueOf(data.get("status")));
        update(updatedOrder);
    }
}
