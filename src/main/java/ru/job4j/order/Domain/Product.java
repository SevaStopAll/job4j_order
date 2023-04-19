package ru.job4j.order.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Product {
    @Getter
    private int id;
    @Getter
    @Setter
    private String name;
}
