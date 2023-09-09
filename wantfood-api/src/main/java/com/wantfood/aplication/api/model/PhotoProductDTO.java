package com.wantfood.aplication.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoProductDTO {
	
//	private ProductDTO product;
	private String description;
	private String contentType;
	private String nameFile;
	private Long size;
}
