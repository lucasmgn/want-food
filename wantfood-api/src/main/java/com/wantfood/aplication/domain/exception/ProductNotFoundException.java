package com.wantfood.aplication.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String Message) {
		super(Message);
	}
	
	public ProductNotFoundException(Long productId, Long restaurantId) {
		this(String.format("Não existe um register de product com código %d para o restaurant de código %d",
				productId, restaurantId));
	}

}
