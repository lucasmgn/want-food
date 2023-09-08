package com.wantfood.aplication.api.assembler;

import com.wantfood.aplication.api.model.input.UserInputDTO;
import com.wantfood.aplication.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInputDisassembler {
	
	@Autowired
    private ModelMapper modelMapper;
	
	public User toDomainObject(UserInputDTO userInputDTO) {
		return modelMapper.map(userInputDTO, User.class);
	}
	
    public void copyToDomainObject(UserInputDTO userInputDTO, User user) {
        modelMapper.map(userInputDTO, user);
    } 
}
