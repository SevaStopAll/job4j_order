package ru.job4j.order.service;

import ru.job4j.order.domain.Customer;

import java.util.Collection;
import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findByName(String customerName);

    Optional<Customer> save(Customer customer);

    Collection<Customer> findAll();

    boolean delete(int id);

}
