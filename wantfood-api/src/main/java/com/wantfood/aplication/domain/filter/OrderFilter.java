package com.wantfood.aplication.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

//Classe DTO que representará propriedades que serão feitas consultas, filtros de pesquisa
@Getter
@Setter
public class OrderFilter {
	
	//Fazendo consulta de orders passando um dos seguintes atributos
	private Long clientId;  
	private Long restaurantId;  
	
	//para n dar exception, problemas em transformar a string em um offsetDateTime
	@DateTimeFormat(iso = ISO.DATE_TIME) 
	private OffsetDateTime creationDateStart;  
	
	@DateTimeFormat(iso = ISO.DATE_TIME) 
	private OffsetDateTime creationDateEnd;  
	
}
