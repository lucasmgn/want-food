package com.wantfood.aplication.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.KitchenDTO;
import com.wantfood.aplication.domain.model.Kitchen;

@Component
public class KitchenDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public KitchenDTO toModel(Kitchen kitchen) {
		return modelMapper.map(kitchen, KitchenDTO.class);
	}
	
	public List<KitchenDTO> toCollectionModel(List<Kitchen> kitchens) {
		return kitchens.stream()
				.map(kitchen -> toModel(kitchen))
				.collect(Collectors.toList());
	}
}
