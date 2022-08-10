package com.wantfood.aplication.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.model.Pedido;
import com.wantfood.aplication.domain.service.EnvioEmailService.Mensagem;

//Classe criada para o fluxo de pedidos: CRIAÇÂO, CONFIMAÇÂO e CANCELAMENTO
@Service
public class FluxoPedidoService {
	
	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@Autowired
	private EnvioEmailService envioEmailService; 
	
	//Método para confirmar o pedido, ele precisa estar com o status criado(Criado - Confirmado)
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigoPedido);					
		pedido.confirmar();
		
		var menssagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + "- Pedido Confirmado meu patrão")
				.corpo("O pedido de código <strong>" + pedido.getCodigo() + "</strong> foi confirmado!")
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		envioEmailService.enviar(menssagem);
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
		pedido.cancelar();;
	}
}
