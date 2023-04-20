package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.order.domain.Status;
import ru.job4j.order.repository.StatusRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class SimpleStatusService implements StatusService {
    private final StatusRepository statuses;

    @Override
    public Collection<Status> findAll() {
        return statuses.findAll();
    }
}
