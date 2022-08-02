package com.wantfood.aplication.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.wantfood.aplication.core.validation.FileContentType;
import com.wantfood.aplication.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {
	
	
	/*
	 * Pode ter no max 1 mb (Padrão)
	 * Utilizando Bean Validation
	 * Criando uma anotação para colocar o valor max
	 * */
	@NotNull
	@FileSize(max = "50KB")
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
}
