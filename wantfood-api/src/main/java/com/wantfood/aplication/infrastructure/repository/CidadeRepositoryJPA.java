package com.wantfood.aplication.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wantfood.aplication.domain.model.Cidade;
import com.wantfood.aplication.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryJPA implements CidadeRepository{

	 @PersistenceContext
     private EntityManager manager;
     
     @Override
     public List<Cidade> todas() {
         return manager.createQuery("from Cidade", Cidade.class)
                 .getResultList();
     }
     
     @Override
     public Cidade porId(Long id) {
         return manager.find(Cidade.class, id);
     }
     
     @Transactional
     @Override
     public Cidade adicionar(Cidade cidade) {
         return manager.merge(cidade);
     }
     
     @Transactional
     @Override
     public void remover(Cidade cidade) {
         cidade = porId(cidade.getId());
         manager.remove(cidade);
     }
	
}
