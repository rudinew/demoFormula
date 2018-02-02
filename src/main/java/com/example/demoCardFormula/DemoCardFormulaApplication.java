package com.example.demoCardFormula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.example.demoCardFormula")
public class DemoCardFormulaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoCardFormulaApplication.class, args);
	}
}
