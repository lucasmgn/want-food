package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.constants.Messages;
import com.wantfood.aplication.domain.exception.EntityInUseException;
import com.wantfood.aplication.domain.exception.StateNotFoundException;
import com.wantfood.aplication.domain.model.State;
import com.wantfood.aplication.domain.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class StateServiceRegistration {

	private final StateRepository stateRepository;
	
	@Transactional
	public State add(State state) {
		return stateRepository.save(state);
	}
	
	public State fetchOrFail(Long stateId) {
		return stateRepository.findById(stateId)
				.orElseThrow(() -> new StateNotFoundException(stateId));
	}
	
	@Transactional
	public void delete(Long stateId) {
		try {
			stateRepository.deleteById(stateId);
			stateRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
			throw  new StateNotFoundException(stateId);
			
		}catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(Messages.STATE_IN_USE, stateId));
		}
	}
}
