package com.wantfood.aplication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wantfood.aplication.domain.model.Cidade;
//Usado para acessar os metodos do JpaRepository
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
}	
