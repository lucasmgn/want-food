package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.constants.Messages;
import com.wantfood.aplication.domain.exception.EntityInUseException;
import com.wantfood.aplication.domain.exception.KitchenNotFoundException;
import com.wantfood.aplication.domain.model.Kitchen;
import com.wantfood.aplication.domain.repository.KitchenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//É um component tbm, porém com mais algumas implementações
@Service
@RequiredArgsConstructor
public class KitchenRegistrationService {
	
	private final KitchenRepository kitchenRepository;
	
	@Transactional
	public Kitchen add(Kitchen kitchen) {
		return kitchenRepository.save(kitchen);
	}
	
	@Transactional //
	public void delete(Long kitchenId) {
		try {
			kitchenRepository.deleteById(kitchenId);
			kitchenRepository.flush(); //Descarrega todas a mudanças no bd
			
		}catch (EmptyResultDataAccessException e) {
			throw new KitchenNotFoundException(kitchenId);
			
		}catch(DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(Messages.KITCHEN_IN_USE, kitchenId));
		}		 
	}
	
	public Kitchen fetchOrFail(Long kitchenId) {
		//buscando kitchen por id e caso não for encontrada lança uma exceção
		return kitchenRepository.findById(kitchenId)
				.orElseThrow(() -> new KitchenNotFoundException(kitchenId));
	}
}
