package com.wantfood.aplication.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.wantfood.aplication.domain.exception.EntidadeNaoEncontradaException;
import com.wantfood.aplication.domain.exception.NegocioException;
import com.wantfood.aplication.domain.model.Pedido;
import com.wantfood.aplication.domain.model.Usuario;
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
	

	/*
	 * Deixando dinâmico as requisição, aparecendo o json apenas do que é colocado no params
	 * @RequestParam(required = false), indica que não é obrigatório colocar o Param
	 * */
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String campos){
//		List<Pedido> todosPedidos = pedidoRepository.findAll();
//		List<PedidoResumoDTO> pedidosDTO = pedidoResumoDTOAssembler.toCollectionModel(todosPedidos);
//		
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosDTO);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//		
//		if (StringUtils.isNotBlank(campos)) {
//			filterProvider.addFilter("pedidoFilter",
//					SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//		}
//		
//		pedidosWrapper.setFilters(filterProvider);
//		
//		return pedidosWrapper;
//	}
	
	@GetMapping
	public List<PedidoResumoDTO> listar(){
		List<Pedido> todosPedidos = pedidoRepository.findAll();
		
		return pedidoResumoDTOAssembler.toCollectionModel(todosPedidos);	
	}
	
	@GetMapping("/{codigoPedido}")
	public PedidoDTO buscar(@PathVariable String codigoPedido) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigoPedido);
	 
		return pedidoDTOAssembler.toModel(pedido);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
		
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInputDTO);
			
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);
			
			novoPedido = cadastroPedidoService.emitir(novoPedido);
			
			return pedidoDTOAssembler.toModel(novoPedido);
		}catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
