package com.example.empleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EmpleadosApplication {

	public static void main(String[] args) {
//		SpringApplication.run(EmpleadosApplication.class, args);
                SpringApplicationBuilder builder = new SpringApplicationBuilder(EmpleadosApplication.class);
                builder.headless(false);
                ConfigurableApplicationContext context = builder.run(args);
	}

}
