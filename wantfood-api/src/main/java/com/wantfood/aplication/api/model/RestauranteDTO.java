package com.wantfood.aplication.api.model;

import java.math.BigDecimal;

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
	
	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaDTO cozinha;

}
