package com.wantfood.aplication.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInputDTO {
	
	@NotBlank
	private String cep;
	
	@NotBlank
	private String logradouro;
	
	@NotBlank
	private String number;

	private String complement;
	
	@NotBlank
	private String neighborhood;
	
	@Valid //para validar as propriedades dentro da classe CityIdInput
	@NotNull
	private CityIdInputDTO city;
}
