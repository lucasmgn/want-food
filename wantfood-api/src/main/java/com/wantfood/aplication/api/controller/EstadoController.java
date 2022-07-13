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

import com.wantfood.aplication.api.assembler.EstadoDTOAssembler;
import com.wantfood.aplication.api.assembler.EstadoInputDisassembler;
import com.wantfood.aplication.api.model.EstadoDTO;
import com.wantfood.aplication.api.model.input.EstadoInputDTO;
import com.wantfood.aplication.domain.model.Estado;
import com.wantfood.aplication.domain.repository.EstadoRepository;
import com.wantfood.aplication.domain.service.CadastroEstadoService;

@RestController //Possui o @ResponsyBody e o @Controller
@RequestMapping("/estados") //
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Autowired
	private EstadoDTOAssembler estadoDTOAssembler;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	
	@GetMapping //Mapeando o metodo listar para quando fizerem uma requisição get
	public List<EstadoDTO> listar(){
		List<Estado> todosEstados = estadoRepository.findAll();
		
		return estadoDTOAssembler.toCollectionModel(todosEstados);
	}
	
	@GetMapping("/{estadoId}")
	public EstadoDTO buscar(@PathVariable Long estadoId){
		
		Estado estado = cadastroEstadoService.buscaOuFalha(estadoId);
		
		return estadoDTOAssembler.toModel(estado);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInputDTO estadoInputDTO) {
Estado estado = estadoInputDisassembler.toDomainObject(estadoInputDTO);
		
		estado = cadastroEstadoService.adicionar(estado);
		
		return estadoDTOAssembler.toModel(estado);
	}
	
	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId,
			@RequestBody @Valid EstadoInputDTO estadoInputDTO){
		
		Estado estadoAtual = cadastroEstadoService.buscaOuFalha(estadoId);
		
		estadoInputDisassembler.copyToDomainObject(estadoInputDTO, estadoAtual);
		
		estadoAtual = cadastroEstadoService.adicionar(estadoAtual);
		
		return estadoDTOAssembler.toModel(estadoAtual);
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId){
		cadastroEstadoService.excluir(estadoId);
	}

}
