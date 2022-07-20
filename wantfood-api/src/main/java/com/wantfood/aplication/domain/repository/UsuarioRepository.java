package com.wantfood.aplication.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.wantfood.aplication.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{
	
	/*
	 * Adicionando um método para verificar os emails já existentes,
	 * fazendo com que sejam aceitos paenas e-mails diferentes
	 * O pprio Spring JPA vai criar a implemnetação que buscarar um usuario ou estará null
	 * */

	Optional<Usuario> findByEmail(String email);
}
