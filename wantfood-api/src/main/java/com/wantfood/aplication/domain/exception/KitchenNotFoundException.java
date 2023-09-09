package com.wantfood.aplication.domain.exception;

public class KitchenNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public KitchenNotFoundException(String Message) {
		super(Message);
	}
	
	public KitchenNotFoundException(Long kitchenId) {
		this(String.format("Não existe register de kitchen com código %d",kitchenId));
	}
	
}
