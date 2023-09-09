package com.wantfood.aplication.api.assembler;

import com.wantfood.aplication.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.input.KitchenInputDTO;

@Component
public class KitchenInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Kitchen toDomainObject(KitchenInputDTO kitchenInputDTO) {
		return modelMapper.map(kitchenInputDTO, Kitchen.class);
	}
	
	public void copyToDomainObject(KitchenInputDTO kitchenInputDTO, Kitchen kitchen) {
		modelMapper.map(kitchenInputDTO, kitchen);
	}
}
