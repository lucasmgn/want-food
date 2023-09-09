package com.wantfood.aplication.api.assembler;

import com.wantfood.aplication.api.model.FormPaymentDTO;
import com.wantfood.aplication.domain.model.FormPayment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormPaymentDTOAssembler {
	
	@Autowired
	ModelMapper modelMapper;
	
	public FormPaymentDTO toModel(FormPayment formPayment) {
		return modelMapper.map(formPayment, FormPaymentDTO.class);
	}
	
	public List<FormPaymentDTO> toCollectionModel(Collection<FormPayment> paymentMethodss) {
		return paymentMethodss.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
}
