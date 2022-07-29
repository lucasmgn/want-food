package com.wantfood.aplication.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wantfood.aplication.domain.model.Pedido;

//Extendendo para JpaSpecificationExecutor para aceitar parametros no find all
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>,
	JpaSpecificationExecutor<Pedido>{

//	@Query("from Pedido where codigo = :codigo")
	Optional<Pedido> findByCodigo(String codigo);
	
	//diminuindo a quantidade de selects na listagem de pedidos
	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	List<Pedido> findAll();
}
