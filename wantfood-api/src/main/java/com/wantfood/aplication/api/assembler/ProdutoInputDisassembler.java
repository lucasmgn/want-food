package com.wantfood.aplication.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.input.ProdutoInputDTO;
import com.wantfood.aplication.domain.model.Product;

@Component
public class ProdutoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Product toDomainObject(ProdutoInputDTO productInputDTO) {
		return modelMapper.map(productInputDTO, Product.class);
	}
	
	public void copyToDomainObject(ProdutoInputDTO productInputDTO, Product product) {
		modelMapper.map(productInputDTO, product);
	}
}
