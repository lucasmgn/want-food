package com.wantfood.aplication.domain.exception;

public class PhotoProductNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public PhotoProductNotFoundException(String Message) {
		super(Message);
	}
	
    public PhotoProductNotFoundException(Long restaurantId, Long productId) {
        this(String.format("Não existe um register de photo do product com código %d para"
        		+ " o restaurant de código %d",
                productId, restaurantId));
    }

}
