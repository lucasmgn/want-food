package com.wantfood.aplication.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wantfood.aplication.api.assembler.CozinhaDTOAssembler;
import com.wantfood.aplication.api.assembler.CozinhaInputDisassembler;
import com.wantfood.aplication.api.model.CozinhaDTO;
import com.wantfood.aplication.api.model.input.CozinhaInputDTO;
import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.repository.CozinhaRepository;
import com.wantfood.aplication.domain.service.CadastroCozinhaService;

//Possui o @ResponsyBody e o @Controller
@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	/*
	 * Mapeando o metodo listar para quando fizerem uma requisição get
	 * Colocando os valores em json
	 * */
	@GetMapping
	public List<CozinhaDTO> listar() {
		List<Cozinha> todasCozinhas = cozinhaRepository.findAll();
		
		return cozinhaDTOAssembler.toCollectionModel(todasCozinhas);
	}

	@GetMapping(value = "/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cadastroCozinha.buscaOuFalha(cozinhaId);
		
		return cozinhaDTOAssembler.toModel(cozinha);
	}
	
	// Colocando o status como create (status 201), e irá retornar a cozinha criada 
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInputDTO);
		cozinha = cadastroCozinha.adicionar(cozinha);
		
		return cozinhaDTOAssembler.toModel(cozinha);
	}

	@PutMapping("/{cozinhaId}")
//	@ResponseStatus(HttpStatus.Up)
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId,
			@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		
		Cozinha cozinhaAtual = cadastroCozinha.buscaOuFalha(cozinhaId);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);
		cozinhaAtual = cadastroCozinha.adicionar(cozinhaAtual);
		
		return cozinhaDTOAssembler.toModel(cozinhaAtual);
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){
		cadastroCozinha.excluir(cozinhaId);
	}

}
