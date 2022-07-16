package com.wantfood.aplication.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoInputDTO {
	
	@NotBlank
	private String descricao;
}
