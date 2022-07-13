package com.wantfood.aplication.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeInputDTO {
	
	@NotBlank
	private String nome;
	
	@Valid
	@NotNull
	private EstadoIdInputDTO estado;
}
