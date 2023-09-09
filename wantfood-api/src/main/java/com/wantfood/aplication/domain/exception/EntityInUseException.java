package com.wantfood.aplication.domain.exception;

//Herança
public class EntityInUseException extends BusinessException{

	private static final long serialVersionUID = 1L;

	public EntityInUseException(String Message) {
		super(Message);
	}
}
