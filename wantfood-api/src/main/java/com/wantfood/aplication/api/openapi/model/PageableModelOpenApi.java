package com.wantfood.aplication.api.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/*
 * classe criada para replace o Pageable da Kitchen controller, feita para usu de documentação,
 * alterei a classe SpringFoxConfig para trocar as classes, Pageable para essa classe
 * 
 * */
@ApiModel("Pageable") //colocando a classe PageableModelOpenApi como Pageable na documentação
@Getter
@Setter
public class PageableModelOpenApi {
	
	@ApiModelProperty(example = "0", value = "Número da página começa em 0")
	private int page;
	
	@ApiModelProperty(example = "10", value = "Quantidade de elementos por página")
	private int size;
	
	@ApiModelProperty(example = "name,asc", value = "name da propriedade em ordem")
	private List<String> sort;

}
