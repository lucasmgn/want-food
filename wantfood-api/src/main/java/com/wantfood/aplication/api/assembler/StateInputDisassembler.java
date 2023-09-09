package com.wantfood.aplication.api.assembler;

import com.wantfood.aplication.api.model.input.StateInputDTO;
import com.wantfood.aplication.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public State toDomainObject(StateInputDTO stateInputDTO) {
		return modelMapper.map(stateInputDTO, State.class);
	}
	
	public void copyToDomainObject(StateInputDTO stateInputDTO, State state) {
		modelMapper.map(stateInputDTO, state);
	}
}
