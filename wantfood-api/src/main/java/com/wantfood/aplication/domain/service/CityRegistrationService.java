package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.constants.Messages;
import com.wantfood.aplication.domain.exception.CityNotFoundException;
import com.wantfood.aplication.domain.exception.EntityInUseException;
import com.wantfood.aplication.domain.model.City;
import com.wantfood.aplication.domain.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CityRegistrationService {
	
	private final CityRepository cityRepository;
	
	private final StateServiceRegistration registerstateService;
	
	@Transactional
	public City add(City city) {
		var stateId = city.getState().getId();

		var state = registerstateService.fetchOrFail(stateId);
		city.setState(state);
		
		 return cityRepository.save(city);
	}
	
	public City fetchOrFail(Long cityId) {
		return cityRepository.findById(cityId)
				.orElseThrow(() -> new CityNotFoundException(cityId));
	}
	
	@Transactional
	public void delete(Long cityId) {
		try {
			cityRepository.deleteById(cityId);
			cityRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CityNotFoundException(cityId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
				String.format(Messages.CITY_IN_USE, cityId));
		}
	}
}
