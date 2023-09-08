package com.wantfood.aplication.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public GroupNotFoundException(String Message) {
		super(Message);
	}	
	
	public GroupNotFoundException(Long groupId) {
		this(String.format("Não existe register de state com código %d.", groupId));
	}

}
