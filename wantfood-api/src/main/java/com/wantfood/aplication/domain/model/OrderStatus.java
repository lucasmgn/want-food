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
	CREATED("Criado"),
	CONFIRMED("Confirmado", CREATED),
	DELIVERED("Entregue", CONFIRMED),
	CANCELED("Cancelado", CREATED);
	
	//Denominando os enuns
	private String description;
	
	private List<OrderStatus> statusAnteriores;
	
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
