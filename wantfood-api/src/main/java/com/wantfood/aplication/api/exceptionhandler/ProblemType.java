package com.wantfood.aplication.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	/*
	 * Criando constantes para serem utilizadas na classe ApiException
	 * */
	RESOURCE_NOT_FOUND("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTITY_IN_USE("/entidade-em-uso", "Entidade em uso"),
	BUSINESS_ERROR("/erro-negocio", "Violação de regra de negócio"),
	ERRO_MESSAGE("/erro-na-Message","Erro na Message"),
	INVALID_PARAMETER("/parametro-invalido", "Parâmetro Inválido"),
	SYSTEM_ERROR("/erro-de-sistema", "Erro de sistema"),
	INVALID_DATA("/dados-invalidos", "Dados inválidos");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title){
		this.uri = "https//wantfood.com.br" + path; 
		this.title = title;
	}
}
