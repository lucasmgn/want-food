package com.wantfood.aplication.domain.repository;

import com.wantfood.aplication.domain.model.Kitchen;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KitchenRepository extends CustomJpaRepository<Kitchen, Long>{
	/*
	 * Mostrar lista de kitchens
	 * Pesquisar kitchen por ID
	 * add kitchen
	 * remove kitchen
	 * */
	
	List<Kitchen> findTodasByNameContaining(String name);
	
	Optional<Kitchen> findByName(String name);
	
	boolean existsByName(String name);
	
}	
