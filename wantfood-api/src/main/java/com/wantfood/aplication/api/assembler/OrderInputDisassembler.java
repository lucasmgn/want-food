package com.wantfood.aplication.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.input.OrderInputDTO;
import com.wantfood.aplication.domain.model.Order;

@Component
public class OrderInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Order toDomainObject(OrderInputDTO orderInputDTO) {
		return modelMapper.map(orderInputDTO, Order.class);
	}
	
	public void copyToDomainObject(OrderInputDTO orderInputDTO, Order order) {
		modelMapper.map(orderInputDTO, order);
	}
}
