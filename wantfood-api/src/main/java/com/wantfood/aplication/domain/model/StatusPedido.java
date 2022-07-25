package com.wantfood.aplication.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {
	/*
	 * Do status criado pode ir para o confirmado, do criado pode ir para o cancelado também
	 * do confirmado pode ir para o entregue
	 * */
	CRIADO("Criado"),
	CONFIRMADO("Confirmado",CRIADO),
	ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO);
	
	//Denominando os enuns
	private String descricao;
	
	private List<StatusPedido> statusAnteriores;
	
	//Passando varags
	StatusPedido(String descricao, StatusPedido... statusAnteriores){
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		//validando
		return !novoStatus.statusAnteriores.contains(this);
	}
}
