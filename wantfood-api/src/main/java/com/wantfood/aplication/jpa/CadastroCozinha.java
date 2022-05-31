package com.wantfood.aplication.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;	

import org.springframework.stereotype.Component;

import com.wantfood.aplication.domain.model.Cozinha;

@Component
public class CadastroCozinha {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Cozinha> listar(){
		return entityManager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}	
}
