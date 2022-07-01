package com.wantfood.aplication.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	/*
	 * Criando constantes para serem utilizadas na classe ApiException
	 * */
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	ERRO_NA_MENSAGEM("/erro-na-mensagem","Erro na mensagem");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title){
		this.uri = "https//wantfood.com.br" + path; 
		this.title = title;
	}
}
