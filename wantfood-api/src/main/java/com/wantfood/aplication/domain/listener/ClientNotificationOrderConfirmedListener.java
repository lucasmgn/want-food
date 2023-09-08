package com.wantfood.aplication.domain.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.wantfood.aplication.domain.event.OrderConfirmedEvent;
import com.wantfood.aplication.domain.model.Order;
import com.wantfood.aplication.domain.service.ShippingEmailService;
import com.wantfood.aplication.domain.service.ShippingEmailService.Message;

@Component
@RequiredArgsConstructor
public class ClientNotificationOrderConfirmedListener {
	
	private final ShippingEmailService sendingEmailService;
	
	/*
	 * Marca o metodo como listener de eventos, sempre que for lançado o evendo esse metodo
	 * será chamado
	 * @TransactionalEventListener, especificando qual a fase especifica da transação que os 
	 * eventos seram desparados
	 * */
	@TransactionalEventListener
	public void confirmOrder(OrderConfirmedEvent event) {
		var order = event.getOrder();
		
		var message = Message.builder()
				.subject(order.getRestaurant().getName() + "- Order Confirmado meu patrão")
				.body("order-confirmado.html")
				.variavel("order", order)
				.recipient(order.getClient().getEmail())
				.build();
		
		sendingEmailService.send(message);
	}
}
