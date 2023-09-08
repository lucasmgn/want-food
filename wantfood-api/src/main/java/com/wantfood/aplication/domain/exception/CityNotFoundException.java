package com.wantfood.aplication.domain.exception;

public class CityNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public CityNotFoundException(String Message) {
		super(Message);
	}
	
	public CityNotFoundException(Long cityId) {
		this(String.format("Não existe register de city com código %d",cityId));
	}
	
}
