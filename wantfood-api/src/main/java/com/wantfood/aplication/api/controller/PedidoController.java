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

import com.wantfood.aplication.api.assembler.PedidoDTOAssembler;
import com.wantfood.aplication.api.assembler.PedidoInputDisassembler;
import com.wantfood.aplication.api.assembler.PedidoResumoDTOAssembler;
import com.wantfood.aplication.api.model.PedidoDTO;
import com.wantfood.aplication.api.model.PedidoResumoDTO;
import com.wantfood.aplication.api.model.input.PedidoInputDTO;
import com.wantfood.aplication.domain.exception.NegocioException;
import com.wantfood.aplication.domain.exception.PedidoNaoEncontradoException;
import com.wantfood.aplication.domain.model.Pedido;
import com.wantfood.aplication.domain.repository.PedidoRepository;
import com.wantfood.aplication.domain.service.CadastroPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@Autowired
	private PedidoDTOAssembler pedidoDTOAssembler;
	
	//Resumo dos atributos pedidos, para ficar uma requisição mais limpa
	@Autowired
	private PedidoResumoDTOAssembler pedidoResumoDTOAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	
	@GetMapping
	public List<PedidoResumoDTO> listar(){
		List<Pedido> todosPedidos = pedidoRepository.findAll();
		
		return pedidoResumoDTOAssembler.toCollectionModel(todosPedidos);	
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoDTO buscar(@PathVariable Long pedidoId) {
		Pedido pedido = cadastroPedidoService.buscaOuFalha(pedidoId);
	 
		return pedidoDTOAssembler.toModel(pedido);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
		
		try {
			Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInputDTO);
			
			pedido = cadastroPedidoService.adicionar(pedido);
			
			return pedidoDTOAssembler.toModel(pedido);
		}catch(PedidoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{pedidoId}")
	public PedidoDTO atualizar(@PathVariable Long pedidoId,
			@RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
		
		try {
			Pedido pedidoAtual = cadastroPedidoService.buscaOuFalha(pedidoId);
			
			pedidoInputDisassembler.copyToDomainObject(pedidoInputDTO, pedidoAtual);
			
			pedidoAtual = cadastroPedidoService.adicionar(pedidoAtual);
			
			return pedidoDTOAssembler.toModel(pedidoAtual);
		}catch(PedidoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
	@DeleteMapping("/{pedidoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long pedidoId) {
		cadastroPedidoService.excluir(pedidoId);
	}
	
}
