package com.wantfood.aplication.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.wantfood.aplication.api.model.EnderecoDTO;
import com.wantfood.aplication.domain.model.Endereco;

@Component
public class ModelMapperConfig {
	
	@Bean
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
		
		var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		//src é o endereco, src é a origem e o destino é feito com 2 argumentos destino e valor 
		//onde será aplicado o valor no destino
		enderecoToEnderecoDTOTypeMap.addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
					(enderecoDTODest, value) -> enderecoDTODest.getCidade().setEstado((String) value));
		
		return modelMapper;
	}
}
