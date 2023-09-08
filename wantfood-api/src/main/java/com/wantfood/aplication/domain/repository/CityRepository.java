package com.wantfood.aplication.domain.repository;

import java.util.List;

import com.wantfood.aplication.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//Usado para acessar os metodos do JpaRepository
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	
	@Query("from City c join fetch c.state")
	List<City> findAll();
}	
