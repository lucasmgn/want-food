package com.wantfood.aplication.domain.exception;

//herança
public class RestaurantNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public RestaurantNotFoundException(String Message) {
		super(Message);
	}
	
	public RestaurantNotFoundException(Long restaurantId) {
		this(String.format("Não existe register de restaurant com código %d.", restaurantId));
	}
	
}
