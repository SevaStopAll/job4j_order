package ru.job4j.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.order.domain.Customer;

import java.util.Collection;
import java.util.Optional;
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByName(String name);

    Collection<Customer> findAll();
}
