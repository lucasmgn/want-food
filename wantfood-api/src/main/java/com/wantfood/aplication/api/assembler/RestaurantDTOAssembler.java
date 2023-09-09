package com.wantfood.aplication.api.assembler;

import com.wantfood.aplication.api.model.RestaurantDTO;
import com.wantfood.aplication.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public RestaurantDTO toModel(Restaurant restaurant) {
		return modelMapper.map(restaurant, RestaurantDTO.class);
	}
	
	public List<RestaurantDTO> toCollectionModel(List<Restaurant> restaurants) {
		return restaurants.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
