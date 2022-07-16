package com.wantfood.aplication.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDTO {
	
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private CidadeResumoDTO cidade;
}
