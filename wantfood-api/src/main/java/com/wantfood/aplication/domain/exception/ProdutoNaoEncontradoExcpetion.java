package com.wantfood.aplication.domain.exception;

public class ProdutoNaoEncontradoExcpetion extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontradoExcpetion(String mensagem) {
		super(mensagem);
	}
	
	public ProdutoNaoEncontradoExcpetion(Long produtoId, Long restauranteId) {
		this(String.format("Não existe um cadastro de produto com código %d para o restaurante de código %d",
				produtoId, restauranteId));
	}

}
