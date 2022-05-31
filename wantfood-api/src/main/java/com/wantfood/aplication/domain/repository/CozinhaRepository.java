package com.wantfood.aplication.domain.repository;

import java.util.List;

import com.wantfood.aplication.domain.model.Cozinha;

public interface CozinhaRepository {
	
	List<Cozinha> todas();
	Cozinha porId(Long id);
	Cozinha adicionar(Cozinha cozinha);
	void remover(Cozinha cozinha);
	
}	
