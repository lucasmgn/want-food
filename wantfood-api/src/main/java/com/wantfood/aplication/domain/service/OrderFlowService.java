package com.wantfood.aplication.domain.service;

import javax.transaction.Transactional;

import com.wantfood.aplication.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.repository.OrderRepository;

//Classe criada para o fluxo de orders: CRIAÇÂO, CONFIMAÇÂO e CANCELAMENTO
@Service
@RequiredArgsConstructor
public class OrderFlowService {
	
	private final ServiceOrderRegistration serviceOrderRegistration;
	
	private final OrderRepository repository;
		
	//Método para confirm o order, ele precisa estar com o status criado(Criado - Confirmado)
	@Transactional
	public void confirm(String codeOrder) {
		var order = serviceOrderRegistration.fetchOrFail(codeOrder);
		order.confirm();
		
		repository.save(order);
	}
	
	//Método para delivered, ele precisa estar com o status confirmado(Confirmado - Entregue)
	@Transactional
	public void deliver(String codeOrder) {
		var order = serviceOrderRegistration.fetchOrFail(codeOrder);
		order.deliver();
	}
	
	//Método para cancelado, ele precisa estar com o status de criado
	@Transactional
	public void cancel(String codeOrder) {
		var order = serviceOrderRegistration.fetchOrFail(codeOrder);
		order.cancel();
		
		repository.save(order);
	}
}
