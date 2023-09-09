package com.wantfood.aplication.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.wantfood.aplication.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.OrderSummaryDTO;

@Component
public class OrderResumeDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public OrderSummaryDTO toModel(Order order) {
		return modelMapper.map(order, OrderSummaryDTO.class);
	}
	
	public List<OrderSummaryDTO> toCollectionModel(Collection<Order> orders) {
		return orders.stream()
				.map(order -> toModel(order))
				.collect(Collectors.toList());
	}
}
