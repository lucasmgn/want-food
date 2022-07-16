package com.wantfood.aplication.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.input.FormaPagamentoInputDTO;
import com.wantfood.aplication.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toDomainObject(FormaPagamentoInputDTO formaPagementoInputDTO) {
		return modelMapper.map(formaPagementoInputDTO, FormaPagamento.class);
	}
	
	public void copyToDomainObject(FormaPagamentoInputDTO formaPagementoInputDTO, FormaPagamento formaPagamento) {
		modelMapper.map(formaPagementoInputDTO, formaPagamento);
	}
}
