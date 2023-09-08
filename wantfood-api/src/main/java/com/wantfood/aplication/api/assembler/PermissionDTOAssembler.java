package com.wantfood.aplication.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.PermissionDTO;
import com.wantfood.aplication.domain.model.Permission;

@Component
public class PermissionDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PermissionDTO toModel(Permission permission) {
		return modelMapper.map(permission, PermissionDTO.class);
	}
	
	public List<PermissionDTO> toCollectionModel(Collection<Permission> permissoes) {
		return permissoes.stream()
				.map(permission -> toModel(permission))
				.collect(Collectors.toList());
	}
}
