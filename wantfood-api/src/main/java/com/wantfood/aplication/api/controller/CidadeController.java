package com.wantfood.aplication.api.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.wantfood.aplication.api.controller.openapi.CidadeControllerOpenApi;
import com.wantfood.aplication.api.model.CidadeDTO;
import com.wantfood.aplication.api.model.input.CidadeInputDTO;
import com.wantfood.aplication.domain.exception.EstadoNaoEncontradoException;
import com.wantfood.aplication.domain.exception.NegocioException;
import com.wantfood.aplication.domain.model.Cidade;
import com.wantfood.aplication.domain.repository.CidadeRepository;
import com.wantfood.aplication.domain.service.CadastroCidadeService;

/*
 * Não funciona pois o OAS_30 não tem descriçao nenhuma
 * @ApiParam(name = "corpo", value = "Representação de uma nova cidade"), alterando o body
 * */
@RestController
@RequestMapping(value = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi{
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeDTOAssembler cidadeDTOAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@GetMapping
	public List<CidadeDTO> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeDTOAssembler.toCollectionModel(todasCidades);
	}
	
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
	Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		return cidadeDTOAssembler.toModel(cidade);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInputDTO);
			
			cidade = cadastroCidade.adicionar(cidade);
			
			return cidadeDTOAssembler.toModel(cidade);
		}catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable Long cidadeId, 
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
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}
}
