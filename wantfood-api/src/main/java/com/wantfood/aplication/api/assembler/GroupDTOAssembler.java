package com.wantfood.aplication.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.GroupDTO;
import com.wantfood.aplication.domain.model.Group;

@Component
public class GroupDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public GroupDTO toModel(Group group) {
		return modelMapper.map(group, GroupDTO.class);
	}
	
	public List<GroupDTO> toCollectionModel(Collection<Group> groups){
		return groups.stream()
				.map(group -> toModel(group))
				.collect(Collectors.toList());
	}
}
