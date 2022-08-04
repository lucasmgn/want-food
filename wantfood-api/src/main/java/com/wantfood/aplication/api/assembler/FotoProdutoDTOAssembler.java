package com.wantfood.aplication.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.FotoProdutoDTO;
import com.wantfood.aplication.domain.model.FotoProduto;

@Component
public class FotoProdutoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoDTO toModel(FotoProduto foto) {
		return modelMapper.map(foto, FotoProdutoDTO.class);
	}
}
