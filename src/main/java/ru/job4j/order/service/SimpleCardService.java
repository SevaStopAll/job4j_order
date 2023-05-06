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

    /**
     * Сохранить новую карту лояльности в базе.
     *
     * @param card карта лояльности.
     * @return Optional карты с id.
     */
    @Override
    public Optional<Card> save(Card card) {
        return Optional.of(cards.save(card));
    }

    /**
     * Найти карту по клиенту.
     *
     * @param customer клиент.
     * @return Optional карты с id.
     */
    @Override
    public Optional<Card> findByCustomer(Customer customer) {
        return cards.findByCustomerId(customer.getId());
    }
}
