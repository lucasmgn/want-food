package com.wantfood.aplication.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.wantfood.aplication.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal freteInicial, BigDecimal freteFinal);
	
	List<Restaurante> findComFreteGratis(String nome);

}