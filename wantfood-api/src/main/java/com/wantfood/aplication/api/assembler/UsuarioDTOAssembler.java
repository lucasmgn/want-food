package com.wantfood.aplication.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.UsuarioDTO;
import com.wantfood.aplication.domain.model.Usuario;

@Component
public class UsuarioDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDTO toModel(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
	
	public List<UsuarioDTO> toCollectionModel(List<Usuario> usuarios){
		return usuarios.stream()
				.map(usuario -> toModel(usuario))
				.collect(Collectors.toList());
	}
}
