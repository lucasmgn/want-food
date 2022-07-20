package com.wantfood.aplication.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.input.GrupoInputDTO;
import com.wantfood.aplication.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainObject(GrupoInputDTO grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoInputDTO grupoInput,  Grupo grupo) {
		modelMapper.map(grupoInput, grupo);
	}
}
