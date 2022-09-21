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

import com.wantfood.aplication.api.assembler.CidadeDTOAssembler;
import com.wantfood.aplication.api.assembler.CidadeInputDisassembler;
import com.wantfood.aplication.api.model.CidadeDTO;
import com.wantfood.aplication.api.model.input.CidadeInputDTO;
import com.wantfood.aplication.domain.exception.EstadoNaoEncontradoException;
import com.wantfood.aplication.domain.exception.NegocioException;
import com.wantfood.aplication.domain.model.Cidade;
import com.wantfood.aplication.domain.repository.CidadeRepository;
import com.wantfood.aplication.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/*
 * 
 * @Api(tags = "Cidades") anotação para marcar o controlador como recuso do swagger
 * @ApiOperation("Lista as cidades") Mudando o nome do metodo na page do Swagger
 * @ApiParam(value = "ID de uma cidade") alterando a descrição no swagger
 * 
 * Não funciona pois o OAS_30 não tem descriçao nenhuma
 * @ApiParam(name = "corpo", value = "Representação de uma nova cidade"), alterando o body
 * */
@Api(tags = "Cidades")
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeDTOAssembler cidadeDTOAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@ApiOperation("Lista as cidades")
	@GetMapping
	public List<CidadeDTO> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeDTOAssembler.toCollectionModel(todasCidades);
	}
	
	@ApiOperation("Busca uma cidade por ID")
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade") @PathVariable Long cidadeId) {
	Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		return cidadeDTOAssembler.toModel(cidade);
	}
	
	@ApiOperation("Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") 
			@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInputDTO);
			
			cidade = cadastroCidade.adicionar(cidade);
			
			return cidadeDTOAssembler.toModel(cidade);
		}catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@ApiOperation("Atualiza uma cidade por ID")
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@ApiParam(value = "ID de uma cidade") @PathVariable Long cidadeId,
			@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		
		try {
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
			
			cidadeInputDisassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);
			
			cidadeAtual = cadastroCidade.adicionar(cidadeAtual);
			
			return cidadeDTOAssembler.toModel(cidadeAtual);
		}catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
	@ApiOperation("Exclui uma cidade por ID")
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "ID de uma cidade") @PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}
}
