package com.wantfood.aplication.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.wantfood.aplication.domain.event.OrderCanceladoEvent;
import com.wantfood.aplication.domain.model.Order;
import com.wantfood.aplication.domain.service.ShippingEmailService;
import com.wantfood.aplication.domain.service.ShippingEmailService.Message;

@Component
public class ClientNotificationOrderCanceledListener {
	
	@Autowired
	private ShippingEmailService sendingEmailService;
	
	/*
	 * Marca o metodo como listener de eventos, sempre que for lançado o evendo esse metodo
	 * será chamado
	 * @TransactionalEventListener, especificando qual a fase especifica da transação que os 
	 * eventos seram desparados
	 * */
	@TransactionalEventListener
	public void cancelOrder(OrderCanceladoEvent event) {
		var order = event.getOrder();
		
		var message = Message.builder()
				.subject(order.getRestaurant().getName() + "- Order Cancelado meu patrão")
				.body("order-cancelado.html")
				.variavel("order", order)
				.recipient(order.getClient().getEmail())
				.build();
		
		sendingEmailService.send(message);
	}
	
}
