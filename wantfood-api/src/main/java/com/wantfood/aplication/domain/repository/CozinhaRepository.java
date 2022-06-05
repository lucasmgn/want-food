package com.wantfood.aplication.domain.repository;

import java.util.List;

import com.wantfood.aplication.domain.model.Cozinha;

public interface CozinhaRepository {
	/*
	 * Mostrar lista de cozinhas
	 * Pesquisar cozinha por ID
	 * adicionar cozinha
	 * remover cozinha
	 * */
	List<Cozinha> todas();
	Cozinha porId(Long id);
	Cozinha adicionar(Cozinha cozinha);
	void remover(Long id);
	
}	
