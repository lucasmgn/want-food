package com.wantfood.aplication.domain.exception;

//Refrencia para as heranças criadas
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BusinessException(String Message) {
		super(Message);
	}
	
	public BusinessException(String Message, Throwable causa) {
		super(Message, causa);
	}
	
}
