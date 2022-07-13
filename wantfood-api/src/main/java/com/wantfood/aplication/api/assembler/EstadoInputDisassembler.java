package com.wantfood.aplication.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.input.EstadoInputDTO;
import com.wantfood.aplication.domain.model.Estado;

@Component
public class EstadoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toDomainObject(EstadoInputDTO estadoInputDTO) {
		return modelMapper.map(estadoInputDTO, Estado.class);
	}
	
	public void copyToDomainObject(EstadoInputDTO estadoInputDTO, Estado estado) {
		modelMapper.map(estadoInputDTO, estado);
	}
}
