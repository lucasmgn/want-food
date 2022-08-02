package com.wantfood.aplication.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wantfood.aplication.api.model.input.FotoProdutoInput;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoControler {
	
	//Criando metodo para carregar foto do produto
	//Coloquei o MultipartFile e a descricao, em uma classe input para diminuir os paremetros
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
		
		//Nome do arquivo 
		var nomeArquivo = UUID.randomUUID().toString() + "_" + 
		fotoProdutoInput.getArquivo().getOriginalFilename();
		
		//Colocando local do arquivo
		var arquivoFoto = Path.of(
				"/Users/lucas/OneDrive/Área de Trabalho/Programação/foto-wantfood", nomeArquivo);
		
		System.out.println(arquivoFoto);
		System.out.println(fotoProdutoInput.getDescricao());
		System.out.println(fotoProdutoInput.getArquivo().getContentType());
		
		try {
			fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
