package com.wantfood.aplication.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wantfood.aplication.api.assembler.FotoProdutoDTOAssembler;
import com.wantfood.aplication.api.model.FotoProdutoDTO;
import com.wantfood.aplication.api.model.input.FotoProdutoInput;
import com.wantfood.aplication.domain.exception.EntidadeNaoEncontradaException;
import com.wantfood.aplication.domain.model.FotoProduto;
import com.wantfood.aplication.domain.model.Produto;
import com.wantfood.aplication.domain.service.CadastroProdutoService;
import com.wantfood.aplication.domain.service.CatalogoFotoProtudoService;
import com.wantfood.aplication.domain.service.FotoStorageService;
import com.wantfood.aplication.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoControler {
	
	@Autowired
	private CatalogoFotoProtudoService fotoProdutoService;
	
	@Autowired
	private CadastroProdutoService produtoService;
	
	@Autowired
	private FotoProdutoDTOAssembler assembler;
	
	@Autowired
	private CatalogoFotoProtudoService fotoProtudoService;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	//Primeiro metodo get será retornado em json
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDTO buscar(@PathVariable Long restauranteId,
			@PathVariable Long produtoId){
			
		FotoProduto fotoProduto = fotoProtudoService.buscarOuFalhar(restauranteId, produtoId);
		
		return assembler.toModel(fotoProduto);
		
	}
	
	//Retornará uma imagem
	@GetMapping
	public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader)
					throws HttpMediaTypeNotAcceptableException{
			
		try {
			FotoProduto fotoProduto = fotoProtudoService.buscarOuFalhar(restauranteId, produtoId);
			
			//Verificando compatibilidade, parseMediaType converte uma string em media type
			MediaType mediaType = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarMediaType(mediaType, mediaTypesAceitas);
			
			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			if (fotoRecuperada.temUrl()) {
				//Retornando a url
				return ResponseEntity.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			}else {
				return ResponseEntity.ok()
						.contentType(mediaType)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		
		Produto produto = produtoService.buscaOuFalha(restauranteId, produtoId);
		
		MultipartFile file = fotoProdutoInput.getArquivo();
		
		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setProduto(produto);
		fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
		fotoProduto.setContentType(file.getContentType());
		fotoProduto.setTamanho(file.getSize());
		fotoProduto.setNomeArquivo(file.getOriginalFilename());
		
		FotoProduto foto = fotoProdutoService.salvar(fotoProduto, file.getInputStream());
		FotoProdutoDTO fotoAssembler = assembler.toModel(foto);
		
		return fotoAssembler;
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId) {
		
		fotoProtudoService.remover(restauranteId, produtoId);
	}
	
	//Metodo de verificação
	private void verificarMediaType(MediaType media, List<MediaType> listaAceitas)
			throws HttpMediaTypeNotAcceptableException {
		
		//Se pelo menos um for compativel retornar true
		boolean compativel = listaAceitas.stream()
				.anyMatch(mediaAceita -> mediaAceita.isCompatibleWith(media));
		
		if(!compativel) {
			throw new HttpMediaTypeNotAcceptableException(listaAceitas);
		}
	}
	
	
}
