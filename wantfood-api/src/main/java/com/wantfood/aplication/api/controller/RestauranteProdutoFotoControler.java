package com.wantfood.aplication.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wantfood.aplication.api.assembler.FotoProdutoDTOAssembler;
import com.wantfood.aplication.api.model.FotoProdutoDTO;
import com.wantfood.aplication.api.model.input.FotoProdutoInput;
import com.wantfood.aplication.domain.model.FotoProduto;
import com.wantfood.aplication.domain.model.Produto;
import com.wantfood.aplication.domain.service.CadastroProdutoService;
import com.wantfood.aplication.domain.service.CatalogoFotoProtudoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoControler {
	
	@Autowired
	private CatalogoFotoProtudoService fotoProdutoService;
	
	@Autowired
	private CadastroProdutoService service;
	
	@Autowired
	private FotoProdutoDTOAssembler assembler;
	
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
		
		Produto produto = service.buscaOuFalha(restauranteId, produtoId);
		
		MultipartFile file = fotoProdutoInput.getArquivo();
		
		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setProduto(produto);
		fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
		fotoProduto.setContentType(file.getContentType());
		fotoProduto.setTamanho(file.getSize());
		fotoProduto.setNomeArquivo(file.getOriginalFilename());
		
		FotoProduto foto = fotoProdutoService.salvar(fotoProduto);
		FotoProdutoDTO fotoAssembler = assembler.toModel(foto);
		
		return fotoAssembler;
	}
}
