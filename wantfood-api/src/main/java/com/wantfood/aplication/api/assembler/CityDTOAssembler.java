package com.wantfood.aplication.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.wantfood.aplication.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.CityDTO;

@Component
public class CityDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CityDTO toModel(City city) {
		return modelMapper.map(city, CityDTO.class);
	}
	
	public List<CityDTO> toCollectionModel(List<City> cities) {
		return cities.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
}
