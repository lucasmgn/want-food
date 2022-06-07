package com.wantfood.aplication.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.wantfood.aplication.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{
	/*
	 * Mostrar lista de cozinhas
	 * Pesquisar cozinha por ID
	 * adicionar cozinha
	 * remover cozinha
	 * */
	
	List<Cozinha> findTodasByNomeContaining(String nome);
	
	Optional<Cozinha> findByNome(String nome);
	
	boolean existsByNome(String nome);
	
}	
