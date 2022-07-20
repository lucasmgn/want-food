package com.wantfood.aplication.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.input.UsuarioInputDTO;
import com.wantfood.aplication.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {
	
	@Autowired
    private ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioInputDTO usuarioInputDTO) {
		return modelMapper.map(usuarioInputDTO, Usuario.class);
	}
	
    public void copyToDomainObject(UsuarioInputDTO usuarioInputDTO, Usuario usuario) {
        modelMapper.map(usuarioInputDTO, usuario);
    } 
}
