package com.wantfood.aplication.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

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
	private OffsetDateTime timestamp;
	private List<Object> objects;
	
	/*
	 *Sendo criado uma classe para listar as causa dos problemas inicialmente
	 * causados por MethodArgumentNotValidException
	 * */
	@Getter
	@Builder
	public static class Object{
		private String name;
		private String userMessage;
	}

}
