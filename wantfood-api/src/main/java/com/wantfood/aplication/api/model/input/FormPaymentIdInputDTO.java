package com.wantfood.aplication.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormPaymentIdInputDTO {
	
	@NotNull
	private Long id;
}
