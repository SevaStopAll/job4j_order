package ru.job4j.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.order.domain.Card;
import ru.job4j.order.domain.Customer;

import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer> {
    Optional<Card> findByCustomerId(int customerId);
}
