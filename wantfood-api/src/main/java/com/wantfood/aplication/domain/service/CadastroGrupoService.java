package com.wantfood.aplication.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.exception.EntidadeEmUsoException;
import com.wantfood.aplication.domain.exception.EstadoNaoEncontradoException;
import com.wantfood.aplication.domain.exception.GrupoNaoEncontradoException;
import com.wantfood.aplication.domain.model.Grupo;
import com.wantfood.aplication.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {
	
    private static final String MSG_GRUPO_EM_USO 
    = "Grupo de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Transactional
	public Grupo adicionar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
		
	@Transactional
	public void excluir(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw  new EstadoNaoEncontradoException(grupoId);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_GRUPO_EM_USO);
		}
	}
	
	public Grupo buscaOuFalha(Long grupoId) {
		return grupoRepository.findById(grupoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}
}
