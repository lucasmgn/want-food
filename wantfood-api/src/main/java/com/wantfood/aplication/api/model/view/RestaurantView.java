package com.wantfood.aplication.api.model.view;

/*
 * Resumindo listagem de restaurant, adicioando o 
 * @JsonView(RestaurantView.Resume.class) nos atributos que
 * ey desejo utilizar na representação e no método list
 * */
public interface RestaurantView {
	
	public interface Resume{
		
	}
	
	public interface JustName{
		
	}
}
