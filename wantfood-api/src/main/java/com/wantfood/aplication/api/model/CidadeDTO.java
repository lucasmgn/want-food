package com.wantfood.aplication.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Setter
@Getter
public class CidadeDTO {
	
	//Anotações do swagger
	@ApiModelProperty(value = "Id da cidade", example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Salvador")
	private String nome;
	private EstadoDTO estado;
}
