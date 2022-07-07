package com.wantfood.aplication.api.model.mixin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.model.Endereco;
import com.wantfood.aplication.domain.model.FormaPagamento;
import com.wantfood.aplication.domain.model.Produto;

//Classe criada para colocar as propriedades que utilizam o jackson
public class RestauranteMixin {
	
	/*
	 * @JsonIgnoreProperties("nome") ignorando a propriedade nome de cozinha na classe restaurante
	 * allowGetters = true aceitando metodos de getter na aplicação
	 * */
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;

	@JsonIgnore 
	private Endereco endereco;
	
	@JsonIgnore 
	private LocalDateTime dataCadastro;
	
	@JsonIgnore 
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>();
}
