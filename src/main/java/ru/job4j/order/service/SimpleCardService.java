package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.order.domain.Card;
import ru.job4j.order.domain.Customer;
import ru.job4j.order.repository.CardRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCardService implements CardService {
    private final CardRepository cards;

    @Override
    public Optional<Card> save(Card card) {
        return Optional.of(cards.save(card));
    }

    @Override
    public Optional<Card> findByCustomer(Customer customer) {
        return cards.findByCustomerId(customer.getId());
    }
}
