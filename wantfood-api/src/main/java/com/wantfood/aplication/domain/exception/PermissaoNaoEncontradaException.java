package com.wantfood.aplication.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public PermissaoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public PermissaoNaoEncontradaException(Long permissaoId) {
		this(String.format("Não existe caddastro de permissão com o código %d", permissaoId));
	}

}
