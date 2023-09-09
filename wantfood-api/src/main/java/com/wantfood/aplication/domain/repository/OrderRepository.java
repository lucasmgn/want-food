package com.wantfood.aplication.domain.repository;

import java.util.List;
import java.util.Optional;

import com.wantfood.aplication.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//Extendendo para JpaSpecificationExecutor para aceitar parametros no find all
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>,
	JpaSpecificationExecutor<Order>{

//	@Query("from Order where code = :code")
	Optional<Order> findBycode(String code);
	
	//diminuindo a amount de selects na listagem de orders
	@Query("from Order p join fetch p.client join fetch p.restaurant r join fetch r.kitchen")
	List<Order> findAll();
}
