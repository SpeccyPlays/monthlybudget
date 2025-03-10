package com.monthlybudget.monthlybudget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.monthlybudget.monthlybudget"})
public class MonthlybudgetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonthlybudgetApplication.class, args);
	}
}

