package ru.job4j.order.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.order.domain.Customer;
import ru.job4j.order.repository.CustomerRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCustomerService implements CustomerService {
    private final CustomerRepository customers;

    @Override
    public Optional<Customer> findByName(String customerName) {
        return customers.findByName(customerName);
    }

    @Override
    public Optional<Customer> save(Customer customer) {
        return Optional.of(customers.save(customer));
    }

    @Override
    public Collection<Customer> findAll() {
        return customers.findAll();
    }

    @Transactional
    public boolean delete(int id) {
        if (customers.findById(id).isPresent()) {
            Customer customer = new Customer();
            customer.setId(id);
            this.customers.delete(customer);
            return true;
        }
        return false;
    }
}
