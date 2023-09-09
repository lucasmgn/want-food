package com.wantfood.aplication.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.wantfood.aplication.domain.model.User;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long>{
	
	/*
	 * Adicionando um método para verificar os emails já existentes,
	 * fazendo com que sejam aceitos paenas e-mails diferentes
	 * O pprio Spring JPA vai create a implemnetação que findar um user ou estará null
	 * */

	Optional<User> findByEmail(String email);
}
