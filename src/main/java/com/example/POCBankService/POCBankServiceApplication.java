package com.example.POCBankService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.POCBankService", "com.example.POCBankService.config"})
public class POCBankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(POCBankServiceApplication.class, args);
	}

}
