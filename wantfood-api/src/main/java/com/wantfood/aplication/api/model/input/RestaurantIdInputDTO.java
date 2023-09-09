package com.wantfood.aplication.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantIdInputDTO {
		
	@NotNull
	private Long id;
}
