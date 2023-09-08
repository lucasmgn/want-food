package com.wantfood.aplication.api.assembler;

import com.wantfood.aplication.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.input.GroupInputDTO;

@Component
public class GroupInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Group toDomainObject(GroupInputDTO groupInput) {
		return modelMapper.map(groupInput, Group.class);
	}
	
	public void copyToDomainObject(GroupInputDTO groupInput,  Group group) {
		modelMapper.map(groupInput, group);
	}
}
