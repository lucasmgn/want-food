package com.wantfood.aplication.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;
import com.wantfood.aplication.api.model.view.RestauranteView;

import lombok.Getter;
import lombok.Setter;

/*
 * Classe criada para manter os atributos que serão usados,   
 *	não sendo interferido caso adicionem atributos novos a classe Restaurante
 *Essa classe será usado como base para as requisições no postman 
 * */
@Getter
@Setter
public class RestauranteDTO {
	/*
	 * Precisa ter os nomes dos atributos das entidades
	 * @JsonView(RestauranteView.Resumo.class), 
	 * marca os atributos que devem fazer parte de uma versão resumida
	 * 
	 * @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class}),
	 * utilizando de acordo com o paremetro 
	 * */
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private Long id;
	
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;
	
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaDTO cozinha;
	
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoDTO endereco;
	

}
