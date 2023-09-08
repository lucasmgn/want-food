package com.wantfood.aplication.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.wantfood.aplication.api.model.view.RestaurantView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/*
 * Classe criada para manter os atributos que serão usados,   
 *	não sendo interferido caso adicionem atributos news a classe restaurant
 *Essa classe será usado como base para as requisições no postman 
 * */
@Getter
@Setter
public class RestaurantDTO {
	/*
	 * Precisa ter os names dos atributos das entidades
	 * @JsonView(RestaurantView.Resume.class), 
	 * marca os atributos que devem fazer parte de uma versão resumida
	 * 
	 * @JsonView({RestaurantView.Resume.class, RestaurantView.JustName.class}),
	 * utilizando de acordo com o paremetro 
	 * */
	@JsonView({RestaurantView.Resume.class, RestaurantView.JustName.class})
	private Long id;
	
	@JsonView({RestaurantView.Resume.class, RestaurantView.JustName.class})
	private String name;
	
	@JsonView(RestaurantView.Resume.class)
	private BigDecimal rateShipping;
	
	@JsonView(RestaurantView.Resume.class)
	private KitchenDTO kitchen;
	
	private Boolean active;
	private Boolean open;
	private AddressDTO address;
	

}
