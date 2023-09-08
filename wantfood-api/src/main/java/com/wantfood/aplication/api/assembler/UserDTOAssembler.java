package com.wantfood.aplication.api.assembler;

import com.wantfood.aplication.api.model.UserDTO;
import com.wantfood.aplication.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UserDTO toModel(User user) {
		return modelMapper.map(user, UserDTO.class);
	}
	
	public List<UserDTO> toCollectionModel(Collection<User> users){
		return users.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
}
