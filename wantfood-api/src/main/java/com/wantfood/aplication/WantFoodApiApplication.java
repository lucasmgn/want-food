package com.wantfood.aplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.wantfood.aplication.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class WantFoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WantFoodApiApplication.class, args);
	}

}
