package com.wantfood.aplication.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.input.CozinhaInputDTO;
import com.wantfood.aplication.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaInputDTO cozinhaInputDTO) {
		return modelMapper.map(cozinhaInputDTO, Cozinha.class);
	}
	
	public void copyToDomainObject(CozinhaInputDTO cozinhaInputDTO, Cozinha cozinha) {
		modelMapper.map(cozinhaInputDTO, cozinha);
	}
}
