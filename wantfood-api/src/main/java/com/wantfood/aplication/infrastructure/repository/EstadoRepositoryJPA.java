package com.wantfood.aplication.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wantfood.aplication.domain.model.Estado;
import com.wantfood.aplication.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryJPA implements EstadoRepository {
	
	 @PersistenceContext
     private EntityManager manager;
     
     @Override
     public List<Estado> todos() {
         return manager.createQuery("from Estado", Estado.class)
                 .getResultList();
     }
     
     @Override
     public Estado porId(Long id) {
         return manager.find(Estado.class, id);
     }
     
     @Transactional
     @Override
     public Estado adicionar(Estado estado) {
         return manager.merge(estado);
     }
     
     @Transactional
     @Override
     public void remover(Estado estado) {
         estado = porId(estado.getId());
         manager.remove(estado);
     }
}
