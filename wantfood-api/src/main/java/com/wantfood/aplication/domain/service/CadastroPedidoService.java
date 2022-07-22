package com.wantfood.aplication.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.exception.CozinhaNaoEncontradaException;
import com.wantfood.aplication.domain.exception.EntidadeEmUsoException;
import com.wantfood.aplication.domain.exception.PedidoNaoEncontradoException;
import com.wantfood.aplication.domain.model.Pedido;
import com.wantfood.aplication.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
	
	private static final String MSG_PEDIDO_EM_USO
	= "Pedido de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public Pedido adicionar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
	
	@Transactional //
	public void excluir(Long pedidoId) {
		try {
			pedidoRepository.deleteById(pedidoId);
			pedidoRepository.flush(); //Descarrega todas a mudanças no bd
			
		}catch (EmptyResultDataAccessException e) {
			throw new PedidoNaoEncontradoException(pedidoId);
			
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_PEDIDO_EM_USO, pedidoId));
		}		 
	}
	
	public Pedido buscaOuFalha(Long pedidoId) {
		//buscando cozinha por id e caso não for encontrada lança uma exceção
		return pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(pedidoId));
	}

}
