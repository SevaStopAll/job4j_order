package ru.job4j.order.Domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    private List<Product> products;
    private Customer customer;
    private Status status;

}
