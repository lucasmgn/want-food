package com.wantfood.aplication.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.PedidoDTO;
import com.wantfood.aplication.domain.model.Pedido;

@Component
public class PedidoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoDTO toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoDTO.class);
	}
	
	public List<PedidoDTO> toCollectionModel(Collection<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}
}
