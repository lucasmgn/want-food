package com.wantfood.aplication.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL) //incluindo as propriedades que não são nulas
@Getter
@Builder //Cria um padrão de projeto
public class Problem {
	/*
	 * Padronizando o formato de problemas
	 * no corpo de respostas com a RFC 7807
	 * */
	private Integer status;
	private String type;
	private String title;
	private String detail;
	
	private String userMessage;

}
