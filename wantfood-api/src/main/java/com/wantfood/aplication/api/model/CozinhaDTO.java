package com.wantfood.aplication.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.wantfood.aplication.api.model.view.RestauranteView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTO {
	
	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
	@JsonView(RestauranteView.Resumo.class)
	private String nome;
}
