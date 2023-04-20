package ru.job4j.order.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.order.domain.Customer;
import ru.job4j.order.service.CardService;
import ru.job4j.order.service.CustomerService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customers;
    private final CardService cards;

    @PostMapping("/registration")
    public ResponseEntity<String> signUp(@RequestBody Customer customer) {
        var customerName = customer.getName();
        var customerPassword = customer.getPassword();
        if (customerName == null || customerPassword == null) {
            throw new NullPointerException("Поля не могут быть пустыми");
        }
        if (!customers.findByName(customerName).isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Этот логин занят.");
        }
        customers.save(customer);
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .contentType(MediaType.TEXT_PLAIN)
                .body("Спасибо за регистрацию! Теперь Вы можете перейти к авторизации.");
    }

    @GetMapping("/")
    public List<Customer> findAll() {
        return (List<Customer>) this.customers.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (customers.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
