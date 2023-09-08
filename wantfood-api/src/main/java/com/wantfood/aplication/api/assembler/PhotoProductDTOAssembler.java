package com.wantfood.aplication.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.PhotoProductDTO;
import com.wantfood.aplication.domain.model.PhotoProduct;

@Component
public class PhotoProductDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PhotoProductDTO toModel(PhotoProduct photo) {
		return modelMapper.map(photo, PhotoProductDTO.class);
	}
}
