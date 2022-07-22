package com.wantfood.aplication.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.input.PedidoInputDTO;
import com.wantfood.aplication.domain.model.Pedido;

@Component
public class PedidoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toDomainObject(PedidoInputDTO pedidoInputDTO) {
		return modelMapper.map(pedidoInputDTO, Pedido.class);
	}
	
	public void copyToDomainObject(PedidoInputDTO pedidoInputDTO, Pedido pedido) {
		modelMapper.map(pedidoInputDTO, pedido);
	}
}
