package com.wantfood.aplication.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CozinhaNaoEncontradaException(Long cozinhaId) {
		this(String.format("Não existe cadastro de cozinha com código %d",cozinhaId));
	}
	
}
