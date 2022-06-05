package com.wantfood.aplication.domain.repository;

import java.util.List;

import com.wantfood.aplication.domain.model.Estado;

public interface EstadoRepository {
	
	List<Estado> todos();
	Estado porId(Long id);
	Estado adicionar(Estado estado);
	void remover(Long id);
	
}	
