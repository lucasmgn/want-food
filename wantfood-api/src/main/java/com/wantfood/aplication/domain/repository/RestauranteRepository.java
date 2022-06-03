package com.wantfood.aplication.domain.repository;

import java.util.List;
import com.wantfood.aplication.domain.model.Restaurante;

public interface RestauranteRepository {
	
	List<Restaurante> todas();
	Restaurante porId(Long id);
	Restaurante adicionar(Restaurante restaurante);
	void remover(Restaurante restaurante);
	
}	
