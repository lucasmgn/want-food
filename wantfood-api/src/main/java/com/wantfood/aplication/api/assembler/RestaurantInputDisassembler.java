package com.wantfood.aplication.api.assembler;

import com.wantfood.aplication.api.model.input.RestaurantInputDTO;
import com.wantfood.aplication.domain.model.City;
import com.wantfood.aplication.domain.model.Kitchen;
import com.wantfood.aplication.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurant toDomainObject(RestaurantInputDTO restaurantInput) {
		return modelMapper.map(restaurantInput, Restaurant.class);
	}
	
	public void copyToDomainObject(RestaurantInputDTO restaurantInput, Restaurant restaurant) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// com.wantfood.domain.model.Kitchen was altered from 1 to 2
		restaurant.setKitchen(new Kitchen());
		
		if(restaurant.getAddress() != null) {
			restaurant.getAddress().setCity(new City());
		}
		
		modelMapper.map(restaurantInput, restaurant);
	}
}
