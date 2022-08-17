package com.wantfood.aplication.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.wantfood.aplication.domain.event.PedidoCanceladoEvent;
import com.wantfood.aplication.domain.model.Pedido;
import com.wantfood.aplication.domain.service.EnvioEmailService;
import com.wantfood.aplication.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCanceladoListener {
	
	@Autowired
	private EnvioEmailService envioEmailService;
	
	/*
	 * Marca o metodo como listener de eventos, sempre que for lançado o evendo esse metodo
	 * será chamado
	 * @TransactionalEventListener, especificando qual a fase especifica da transação que os 
	 * eventos seram desparados
	 * */
	@TransactionalEventListener
	public void cancelarPedido(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido();
		
		var menssagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + "- Pedido Cancelado meu patrão")
				.corpo("pedido-cancelado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		envioEmailService.enviar(menssagem);
	}
	
}
