package com.wantfood.aplication.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "City", description = "Representa uma city")
@Setter
@Getter
public class CityDTO {
	
	//Anotações do swagger
	@ApiModelProperty(value = "Id da city", example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Salvador")
	private String name;
	private StateDTO state;
}
