package com.wantfood.aplication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wantfood.aplication.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{
	
}	
