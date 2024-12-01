package com.example.SpringProject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringProject1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringProject1Application.class, args);
	}

}
