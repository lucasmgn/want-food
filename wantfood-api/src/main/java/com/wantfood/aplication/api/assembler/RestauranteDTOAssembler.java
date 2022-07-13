package com.wantfood.aplication.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.RestauranteDTO;
import com.wantfood.aplication.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteDTO toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDTO.class);
	}
	
	public List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
	
}
