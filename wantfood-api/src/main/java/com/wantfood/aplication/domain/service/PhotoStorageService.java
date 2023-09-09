package com.wantfood.aplication.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PhotoStorageService {
	
	void store(NewPicture novaFoto);
	
	void remove(String nameFile);
	
	PhotoRecovered recover(String nameFile);
	
	default void replace(String nameFileOld, NewPicture newPicture) {
		this.store(newPicture);
		if(nameFileOld != null) {
			this.remove(nameFileOld);
		}
	}
	
	default String generateNameFile(String nameFile) {
		return UUID.randomUUID().toString() + "_" + nameFile;
	}
	
	//Criando uma classe interna
	@Getter
	@Builder
	class NewPicture{
		
		private String nameFile;
		private String contentType;
		
		//Fluxo de entrada do file
		private InputStream inputStream;
	}
	
	@Getter
	@Builder
	class PhotoRecovered{
		private InputStream inputStream;
		private String url;
		
		public boolean temUrl() {
			
			return url != null;
		}
		
		public boolean temInputStream() {
			
			return inputStream != null;
		}
	}
}
