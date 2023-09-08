package com.wantfood.aplication.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantInputDTO {
	
	@NotBlank
	private String name;
	
	@NotNull 
	@PositiveOrZero
	private BigDecimal rateShipping;
	
	@Valid
	@NotNull
	private KitchenIdInputDTO kitchen;
	
	@Valid
	@NotNull
	private AddressInputDTO address;
}
