package com.wantfood.aplication.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInputDTO {
	
	@NotBlank
	private String cep;
	
	@NotBlank
	private String logradouro;
	
	@NotBlank
	private String numero;

	private String complemento;
	
	@NotBlank
	private String bairro;
	
	@Valid //para validar as propriedades dentro da classe CidadeIdInput
	@NotNull
	private CidadeIdInputDTO cidade;
}
