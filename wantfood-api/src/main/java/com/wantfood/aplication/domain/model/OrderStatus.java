package com.wantfood.aplication.domain.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum OrderStatus {
	/*
	 * Do status criado pode ir para o confirmado, do criado pode ir para o cancelado também
	 * do confirmado pode ir para o delivered
	 * */
	CREATED("Created"),
	CONFIRMED("Confirmed", CREATED),
	DELIVERED("Delivered", CONFIRMED),
	CANCELED("Canceled", CREATED);
	
	//Denominando os enuns
	private final String description;
	
	private final List<OrderStatus> statusAnteriores;
	
	//Passando varags
	OrderStatus(String description, OrderStatus... statusAnteriores){
		this.description = description;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public boolean naoPodeAlterarPara(OrderStatus newStatus) {
		//validando
		return !newStatus.statusAnteriores.contains(this);
	}
}
