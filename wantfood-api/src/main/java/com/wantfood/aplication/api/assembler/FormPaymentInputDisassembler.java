package com.wantfood.aplication.api.assembler;

import com.wantfood.aplication.api.model.input.FormPaymentInputDTO;
import com.wantfood.aplication.domain.model.FormPayment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormPaymentInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FormPayment toDomainObject(FormPaymentInputDTO formaPagementoInputDTO) {
		return modelMapper.map(formaPagementoInputDTO, FormPayment.class);
	}
	
	public void copyToDomainObject(FormPaymentInputDTO formaPagementoInputDTO, FormPayment formPayment) {
		modelMapper.map(formaPagementoInputDTO, formPayment);
	}
}
