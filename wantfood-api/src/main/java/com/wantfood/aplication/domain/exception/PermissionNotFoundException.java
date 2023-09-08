package com.wantfood.aplication.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public PermissionNotFoundException(String Message) {
		super(Message);
	}
	
	public PermissionNotFoundException(Long permissionId) {
		this(String.format("Não existe caddastro de permissão com o código %d", permissionId));
	}

}
