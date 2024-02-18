package com.msj.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class MyappBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyappBackendApplication.class, args);
	}
}
