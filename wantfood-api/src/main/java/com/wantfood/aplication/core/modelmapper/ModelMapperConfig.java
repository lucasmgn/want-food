package com.wantfood.aplication.core.modelmapper;

import com.wantfood.aplication.api.model.AddressDTO;
import com.wantfood.aplication.api.model.input.ItemOrderInputDTO;
import com.wantfood.aplication.domain.model.Address;
import com.wantfood.aplication.domain.model.ItemOrder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConfig {
	
	@Bean
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
//		modelMapper.createTypeMap(restaurant.class, restaurantModel.class)
//			.addMapping(restaurant::getrateShipping, restaurantModel::setPrecoFrete);
		
		var addressToAddressDTOTypeMap = modelMapper.createTypeMap(Address.class, AddressDTO.class);
		
		//src é o address, src é a origem e o destino é feito com 2 argumentos destino e valor 
		//onde será aplicado o valor no destino
		addressToAddressDTOTypeMap.addMapping(
				addressSrc -> addressSrc.getCity().getState().getName(),
					(addressDTODest, value) -> addressDTODest.getCity().setState((String) value));
		
		modelMapper.createTypeMap(ItemOrderInputDTO.class, ItemOrder.class)
	    .addMappings(mapper -> mapper.skip(ItemOrder::setId)); 
		
		return modelMapper; 
	}
}
