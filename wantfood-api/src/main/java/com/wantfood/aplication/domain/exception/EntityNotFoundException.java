package com.wantfood.aplication.domain.exception;

//Herança
public class EntityNotFoundException extends BusinessException{

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String Message) {
		super(Message);
	}
	
}
