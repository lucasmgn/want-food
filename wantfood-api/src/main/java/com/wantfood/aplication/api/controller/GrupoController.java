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

import com.wantfood.aplication.api.assembler.GrupoDTOAssembler;
import com.wantfood.aplication.api.assembler.GrupoInputDisassembler;
import com.wantfood.aplication.api.model.GrupoDTO;
import com.wantfood.aplication.api.model.input.GrupoInputDTO;
import com.wantfood.aplication.domain.model.Grupo;
import com.wantfood.aplication.domain.repository.GrupoRepository;
import com.wantfood.aplication.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoDTOAssembler grupoDTOAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;
	
	@GetMapping
	public List<GrupoDTO> listar(){
		List<Grupo> todosGrupos = grupoRepository.findAll();
		
		return grupoDTOAssembler.toCollectionModel(todosGrupos);
	}
	
	@GetMapping("/{grupoId}")
	public GrupoDTO buscar(@PathVariable @Valid Long grupoId) {
		Grupo grupo = cadastroGrupoService.buscaOuFalha(grupoId);
		
		return grupoDTOAssembler.toModel(grupo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody @Valid GrupoInputDTO grupoInputDTO) {
		
		Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInputDTO);
		grupo = cadastroGrupoService.adicionar(grupo);
		
		return grupoDTOAssembler.toModel(grupo);
	}
	
	@PutMapping("/{grupoId}")
	public GrupoDTO atualizar(@PathVariable Long grupoId,
			@RequestBody @Valid GrupoInputDTO grupoInputDTO) {
		Grupo grupoAtual = cadastroGrupoService.buscaOuFalha(grupoId);
		
		grupoInputDisassembler.copyToDomainObject(grupoInputDTO, grupoAtual);
		
		grupoAtual = cadastroGrupoService.adicionar(grupoAtual);
		
		return grupoDTOAssembler.toModel(grupoAtual);
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		cadastroGrupoService.excluir(grupoId);
	}
	
}
