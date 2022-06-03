package com.wantfood.aplication.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wantfood.aplication.domain.model.Restaurante;
import com.wantfood.aplication.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryJPA implements RestauranteRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	//Listar restaurante
	@Override
	public List<Restaurante> todas(){
		return entityManager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}	
	
	//Buscar apenas um item
	@Override
	public Restaurante porId(Long id) {
		return entityManager.find(Restaurante.class, id);
	}
	
	@Transactional //O métedo será executado dentro de uma transação, adicionar restaurante
	@Override
	public Restaurante adicionar(Restaurante restaurante) {
		return entityManager.merge(restaurante);
	}
	
	//Removendo restaurante
	@Transactional
	@Override
	public void remover(Restaurante restaurante) {
		restaurante = porId(restaurante.getId());
		entityManager.remove(restaurante);
	}
	
}
