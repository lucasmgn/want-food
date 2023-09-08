package com.wantfood.aplication.domain.event;

import com.wantfood.aplication.domain.model.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderConfirmedEvent {
	
	private Order order;
	
}
