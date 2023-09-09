package com.wantfood.aplication.domain.repository;

import com.wantfood.aplication.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository
		extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {
	
	List<Restaurant> findAll();

	List<Restaurant> findByRateShippingBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
//	@Query("from restaurant where name like %:name% and kitchen.id = :id"), usei o orm.xml para importar a query
//	List<Restaurant> findByName(String name, @Param("id") Long kitchen);
	
//	List<restaurant> findBynameContainingAndKitchenId(String name, Long kitchen);
	
	Optional<Restaurant> findFirstRestaurantByNameContaining(String name);
	
	List<Restaurant> findTop2ByNameContaining(String name);
	
	int countByKitchenId(Long kitchen);
	
}	
