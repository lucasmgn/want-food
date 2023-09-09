package com.wantfood.aplication.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityIdInputDTO {
	
	@NotNull
	private Long id;
}
