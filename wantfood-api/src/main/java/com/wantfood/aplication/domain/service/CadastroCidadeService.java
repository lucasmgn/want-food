package com.wantfood.aplication.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.exception.CidadeNaoEncontradaException;
import com.wantfood.aplication.domain.exception.EntidadeEmUsoException;
import com.wantfood.aplication.domain.model.Cidade;
import com.wantfood.aplication.domain.model.Estado;
import com.wantfood.aplication.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	
	private static final String MSG_CIDADE_ESTA_EM_USO
	= "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Transactional
	public Cidade adicionar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();

		Estado estado = cadastroEstadoService.buscaOuFalha(estadoId);
		cidade.setEstado(estado);
		
		 return cidadeRepository.save(cidade);
	}
	
	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}
	
	@Transactional
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_CIDADE_ESTA_EM_USO, cidadeId));
		}
	}
}
