package com.wantfood.aplication.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.input.ProductInputDTO;
import com.wantfood.aplication.domain.model.Product;

@Component
public class ProductInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Product toDomainObject(ProductInputDTO productInputDTO) {
		return modelMapper.map(productInputDTO, Product.class);
	}
	
	public void copyToDomainObject(ProductInputDTO productInputDTO, Product product) {
		modelMapper.map(productInputDTO, product);
	}
}
