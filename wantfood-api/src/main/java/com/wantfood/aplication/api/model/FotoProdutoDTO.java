package com.wantfood.aplication.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoDTO {
	
//	private ProdutoDTO produto;
	private String descricao;
	private String contentType;
	private String nomeArquivo;
	private Long tamanho;
}
