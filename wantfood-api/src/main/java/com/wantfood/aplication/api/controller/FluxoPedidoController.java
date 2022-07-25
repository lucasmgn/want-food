package com.wantfood.aplication.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wantfood.aplication.domain.service.FluxoPedidoService;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class FluxoPedidoController {
	
	@Autowired
	private FluxoPedidoService fluxoPedidoService;
	
	//Confirmando o pedido, utilizando o método confirmar da classe de serviço 
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable String codigoPedido) {
		 fluxoPedidoService.confirmar(codigoPedido);
	}
	
	//Entrega do pedido, utilizando o método entregue da classe de serviço 
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregue(@PathVariable String codigoPedido) {
		 fluxoPedidoService.entregar(codigoPedido);
	}
	
	//Cancelamento do pedido, utilizando o método cancelar da classe de serviço 
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable String codigoPedido) {
		 fluxoPedidoService.cancelar(codigoPedido);
	}
}
