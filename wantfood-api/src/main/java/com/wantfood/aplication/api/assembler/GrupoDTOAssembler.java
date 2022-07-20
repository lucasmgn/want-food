package com.wantfood.aplication.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.GrupoDTO;
import com.wantfood.aplication.domain.model.Grupo;

@Component
public class GrupoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public GrupoDTO toModel(Grupo grupo) {
		return modelMapper.map(grupo, GrupoDTO.class);
	}
	
	public List<GrupoDTO> toCollectionModel(List<Grupo> grupos){
		return grupos.stream()
				.map(grupo -> toModel(grupo))
				.collect(Collectors.toList());
	}
}
