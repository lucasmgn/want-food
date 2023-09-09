package com.wantfood.aplication.domain.exception;

public class FormPaymentNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public FormPaymentNotFoundException(String Message) {
		super(Message);
	}
	
	public FormPaymentNotFoundException(Long formPaymentId) {
		 this(String.format("Não existe um register de forma de pagamento com código %d", formPaymentId));
	}

}
