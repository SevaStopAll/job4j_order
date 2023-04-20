package ru.job4j.order.service;

import ru.job4j.order.domain.Status;

import java.util.Collection;

public interface StatusService {
    Collection<Status> findAll();
}
