package com.wantfood.aplication.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	/*
	 * Criando constantes para serem utilizadas na classe ApiException
	 * */
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	ERRO_NA_Message("/erro-na-Message","Erro na Message"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro Inválido"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title){
		this.uri = "https//wantfood.com.br" + path; 
		this.title = title;
	}
}
