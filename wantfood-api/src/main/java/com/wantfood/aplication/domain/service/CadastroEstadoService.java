package com.wantfood.aplication.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.exception.EntidadeEmUsoException;
import com.wantfood.aplication.domain.exception.EstadoNaoEncontradoException;
import com.wantfood.aplication.domain.model.Estado;
import com.wantfood.aplication.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	private static final String MSG_ESTADO_ESTA_EM_USO
	= "Estado de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Transactional
	public Estado adicionar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public Estado buscaOuFalha(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
	}
	
	@Transactional
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
			estadoRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
			throw  new EstadoNaoEncontradoException(estadoId);
			
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_ESTA_EM_USO, estadoId));
		}
	}
}
