package com.wantfood.aplication.domain.repository;

import com.wantfood.aplication.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {

	List<Restaurant> find(String name, BigDecimal freteInicial, BigDecimal freteFinal);
	
	List<Restaurant> findComFreteGratis(String name);

}