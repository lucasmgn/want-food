package com.wantfood.aplication.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String Message) {
		super(Message);
	}
	
	public UserNotFoundException(Long userId) {
		this(String.format("Não existe register de usuário com o código %d", userId));
	}
}
