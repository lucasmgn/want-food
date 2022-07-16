package com.wantfood.aplication.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.exception.CozinhaNaoEncontradaException;
import com.wantfood.aplication.domain.exception.EntidadeEmUsoException;
import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.repository.CozinhaRepository;

//É um component tbm, porém com mais algumas implementações
@Service
public class CadastroCozinhaService {
	
	private static final String MSG_COZINHA_EM_USO
		= "Cozinha de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Transactional
	public Cozinha adicionar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	@Transactional //
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
			cozinhaRepository.flush(); //Descarrega todas a mudanças no bd
			
		}catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);
			
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}		 
	}
	
	public Cozinha buscaOuFalha(Long cozinhaId) {
		//buscando cozinha por id e caso não for encontrada lança uma exceção
		return cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}
}
