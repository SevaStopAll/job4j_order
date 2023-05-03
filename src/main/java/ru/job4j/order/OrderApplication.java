package ru.job4j.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Bean
	public String returnString() {
		String str = "http://localhost:8080/dishes/";
		return str;
	}

	@Bean
	public RestTemplate returnRest() {
		RestTemplate client = new RestTemplate();
		return client;
	}
}

