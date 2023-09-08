package com.wantfood.aplication.domain.exception;

//Herança
public class StateNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public StateNotFoundException(String Message) {
		super(Message);
	}
	
	public StateNotFoundException(Long stateId) {
		this(String.format("Não existe register de state com código %d.",stateId));
	}
	
}
