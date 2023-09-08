package com.wantfood.aplication.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.OrderDTO;
import com.wantfood.aplication.domain.model.Order;

@Component
public class OrderDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public OrderDTO toModel(Order order) {
		return modelMapper.map(order, OrderDTO.class);
	}
	
	public List<OrderDTO> toCollectionModel(Collection<Order> orders) {
		return orders.stream()
				.map(order -> toModel(order))
				.collect(Collectors.toList());
	}
}
