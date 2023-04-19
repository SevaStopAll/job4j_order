package ru.job4j.order.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.order.Domain.Status;

public interface StatusRepository extends CrudRepository<Status, Integer> {
}
