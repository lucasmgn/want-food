package com.wantfood.aplication.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.FormaPagamentoDTO;
import com.wantfood.aplication.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler {
	
	@Autowired
	ModelMapper modelMapper;
	
	public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
	
	public List<FormaPagamentoDTO> toCollectionModel(List<FormaPagamento> formasPagamentos) {
		return formasPagamentos.stream()
				.map(formasPagamento -> toModel(formasPagamento))
				.collect(Collectors.toList());
	}
}
