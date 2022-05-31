package com.wantfood.aplication.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.repository.CozinhaRepository;

@Component
public class CozinhaRepositoryJPA implements CozinhaRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	//Listar cozinhas
	@Override
	public List<Cozinha> todas(){
		return entityManager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}	
	
	//Buscar apenas um item
	@Override
	public Cozinha porId(Long id) {
		return entityManager.find(Cozinha.class, id);
	}
	
	@Transactional //O métedo será executado dentro de uma transação, adicionar cozinha
	@Override
	public Cozinha adicionar(Cozinha cozinha) {
		return entityManager.merge(cozinha);
	}
	
	//Removendo cozinha
	@Transactional
	@Override
	public void remover(Cozinha cozinha) {
		cozinha = porId(cozinha.getId());
		entityManager.remove(cozinha);
	}
	
}
