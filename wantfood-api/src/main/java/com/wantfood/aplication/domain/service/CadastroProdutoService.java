package com.wantfood.aplication.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.exception.EntidadeEmUsoException;
import com.wantfood.aplication.domain.exception.FormaPagamentoNaoEncontradaException;
import com.wantfood.aplication.domain.exception.ProdutoNaoEncontradoExcpetion;
import com.wantfood.aplication.domain.model.Produto;
import com.wantfood.aplication.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {
	
	private static final String MSG_PRODUTO_EM_USO 
    = "Produto de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Produto buscaOuFalha(Long restauranteId, Long produtoId) {
		return produtoRepository.findById(restauranteId, produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradoExcpetion(produtoId, restauranteId));
	}
	
	@Transactional
	public void excluir(Long produtoId) {
		try {
			produtoRepository.deleteById(produtoId);
			produtoRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
			throw  new FormaPagamentoNaoEncontradaException(produtoId);
			
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_PRODUTO_EM_USO, produtoId));
		}
	}
}
