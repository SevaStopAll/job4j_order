package ru.job4j.order.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.order.Domain.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
