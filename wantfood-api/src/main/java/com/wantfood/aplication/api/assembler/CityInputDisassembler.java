package com.wantfood.aplication.api.assembler;

import com.wantfood.aplication.api.model.input.CityInputDTO;
import com.wantfood.aplication.domain.model.City;
import com.wantfood.aplication.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public City toDomainObject(CityInputDTO cityInput) {
		return modelMapper.map(cityInput, City.class);
	}
	
		public void copyToDomainObject(CityInputDTO cityInput, City city) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// com.wantfood.domain.model.state was altered from 1 to 2
		city.setState(new State());
		
		modelMapper.map(cityInput, city);
	}
}
