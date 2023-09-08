package com.wantfood.aplication.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.wantfood.aplication.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.ProductDTO;

@Component
public class ProdutoDTOAssembler {
	
	@Autowired
	ModelMapper modelMapper;
	
	public ProductDTO toModel(Product product) {
		return modelMapper.map(product, ProductDTO.class);
	}
	
	public List<ProductDTO> toCollectionModel(List<Product> products){
		return products.stream()
				.map(product -> toModel(product))
				.collect(Collectors.toList());
	}
}
