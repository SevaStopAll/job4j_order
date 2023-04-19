package ru.job4j.order.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Status {
    @Getter
    private int id;
    @Getter
    private String name;
}
