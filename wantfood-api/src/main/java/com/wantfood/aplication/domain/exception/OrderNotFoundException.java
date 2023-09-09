package com.wantfood.aplication.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException{
	
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(String codeOrder) {
		super(String.format("Não existe register de order com código %s.",codeOrder));
	}
}
