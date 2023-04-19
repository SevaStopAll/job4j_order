package ru.job4j.order.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Card {
    @Getter
    private int id;
    private Customer customer;
}
