package com.wantfood.aplication.api.model.view;

/*
 * Resumindo listagem de restaurante, adicioando o 
 * @JsonView(RestauranteView.Resumo.class) nos atributos que
 * ey desejo utilizar na representação e no método listar
 * */
public interface RestauranteView {
	
	public interface Resumo{
		
	}
	
	public interface ApenasNome{
		
	}
}
