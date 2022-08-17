package com.wantfood.aplication.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.model.Pedido;
import com.wantfood.aplication.domain.repository.PedidoRepository;

//Classe criada para o fluxo de pedidos: CRIAÇÂO, CONFIMAÇÂO e CANCELAMENTO
@Service
public class FluxoPedidoService {
	
	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	//Instanciado para poder utilizar o evento
	@Autowired
	private PedidoRepository repository;
		
	//Método para confirmar o pedido, ele precisa estar com o status criado(Criado - Confirmado)
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigoPedido);					
		pedido.confirmar();
		
		repository.save(pedido);
	}
	
	//Método para entregue, ele precisa estar com o status confirmado(Confirmado - Entregue)
	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}
	
	//Método para cancelado, ele precisa estar com o status de criado
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
		
		repository.save(pedido);
	}
}
