package com.wantfood.aplication.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
	
	private String code;
	private BigDecimal subtotal;
	private BigDecimal rateShipping;	
	private BigDecimal amount;
    private AddressDTO addressDelivery;
	private String status;
	private OffsetDateTime creationDate; 
	private OffsetDateTime dateConfirmation;
	private OffsetDateTime dateCancellation;
	private OffsetDateTime deliveryDate;
	private FormPaymentDTO formPayment;
	private RestaurantSummaryDTO restaurant;
	private UserDTO client;
	private List<ItemOrderDTO> items; 
}
