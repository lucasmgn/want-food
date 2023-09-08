package com.wantfood.aplication.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//@JsonFilter("orderFilter") Remover pois dar error quando utiliza o Squiggly 
@Getter
@Setter
public class OrderSummaryDTO {
	
	private String code; 
	private BigDecimal subtotal;
	private BigDecimal rateShipping;	
	private BigDecimal amount;
	private String status;
	private OffsetDateTime creationDate; 
	private RestaurantSummaryDTO restaurant;
	private UserDTO client;
}
