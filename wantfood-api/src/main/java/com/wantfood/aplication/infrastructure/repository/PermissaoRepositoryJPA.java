package com.wantfood.aplication.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wantfood.aplication.domain.model.Permissao;
import com.wantfood.aplication.domain.repository.PermissaoRepository;

@Component
public class PermissaoRepositoryJPA implements PermissaoRepository{
	
	@PersistenceContext
    private EntityManager manager;
    
    @Override
    public List<Permissao> todas() {
        return manager.createQuery("from Permissao", Permissao.class)
                .getResultList();
    }
    
    @Override
    public Permissao porId(Long id) {
        return manager.find(Permissao.class, id);
    }
    
    @Transactional
    @Override
    public Permissao adicionar(Permissao permissao) {
        return manager.merge(permissao);
    }
    
    @Transactional
    @Override
    public void remover(Permissao permissao) {
        permissao = porId(permissao.getId());
        manager.remove(permissao);
    }
}
