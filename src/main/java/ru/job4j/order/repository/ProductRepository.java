package ru.job4j.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.order.domain.Product;
@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
}