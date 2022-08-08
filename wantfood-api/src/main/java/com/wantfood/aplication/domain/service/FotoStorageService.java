package com.wantfood.aplication.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	void armazenar(NovaFoto novaFoto);
	
	void remover(String nomeArquivo);
	
	InputStream recuperar(String nomeArquivo);
	
	default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
		this.armazenar(novaFoto);
		if(nomeArquivoAntigo != null) {
			this.remover(nomeArquivoAntigo);
		}
	}
	
	default String gerarNomeArquivo(String nomeArquivo) {
		return UUID.randomUUID().toString() + "_" + nomeArquivo;
	}
	
	//Criando uma classe interna
	@Getter
	@Builder
	class NovaFoto{
		
		private String nomeArquivo;
		
		//Fluxo de entrada do arquivo
		private InputStream inputStream;
	}
}
