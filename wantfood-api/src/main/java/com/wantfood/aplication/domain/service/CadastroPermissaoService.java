package com.wantfood.aplication.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.exception.PermissaoNaoEncontradaException;
import com.wantfood.aplication.domain.model.Permissao;
import com.wantfood.aplication.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {
	
    @Autowired
    private PermissaoRepository permissaoRepository;
    
    public Permissao buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
            .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}
