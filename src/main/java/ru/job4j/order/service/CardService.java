package ru.job4j.order.service;

import ru.job4j.order.Domain.Card;
import ru.job4j.order.Domain.Customer;

import java.util.Optional;

public interface CardService {

    Optional<Card> save(Card card);

    Optional<Card> findByCustomer(Customer customer);
}
