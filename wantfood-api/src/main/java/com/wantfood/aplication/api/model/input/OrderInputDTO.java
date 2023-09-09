package com.wantfood.aplication.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class OrderInputDTO {
	
	@Valid
	@NotNull
	private RestaurantIdInputDTO restaurant;
	
	@Valid
	@NotNull
	private FormPaymentIdInputDTO formPayment;
	
	@Valid
	@NotNull
	private AddressInputDTO addressDelivery;
	
	@Valid
	@Size(min = 1)
	@NotNull
	private List<ItemOrderInputDTO> items;
}
