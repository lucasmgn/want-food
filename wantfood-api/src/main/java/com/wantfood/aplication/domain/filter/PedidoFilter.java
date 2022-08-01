package com.wantfood.aplication.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

//Classe DTO que representará propriedades que serão feitas consultas, filtros de pesquisa
@Getter
@Setter
public class PedidoFilter {
	
	//Fazendo consulta de pedidos passando um dos seguintes atributos
	private Long clienteId;  
	private Long restauranteId;  
	
	//para n dar exception, problemas em transformar a string em um offsetDateTime
	@DateTimeFormat(iso = ISO.DATE_TIME) 
	private OffsetDateTime dataCriacaoInicio;  
	
	@DateTimeFormat(iso = ISO.DATE_TIME) 
	private OffsetDateTime dataCriacaoFim;  
	
}
