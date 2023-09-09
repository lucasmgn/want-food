package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.constants.Messages;
import com.wantfood.aplication.domain.exception.EntityInUseException;
import com.wantfood.aplication.domain.exception.FormPaymentNotFoundException;
import com.wantfood.aplication.domain.model.FormPayment;
import com.wantfood.aplication.domain.repository.FormPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationFormPaymentService {
	
	private final FormPaymentRepository formPaymentRepository;
	
	@Transactional
	public FormPayment add(FormPayment formPayment) {
		return formPaymentRepository.save(formPayment);
	}
	
	public FormPayment fetchOrFail(Long formPaymentId) {
		return formPaymentRepository.findById(formPaymentId)
				.orElseThrow(() -> new FormPaymentNotFoundException(formPaymentId));
	}
	
	@Transactional
	public void delete(Long formPaymentId) {
		try {
			formPaymentRepository.deleteById(formPaymentId);
			formPaymentRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
			throw  new FormPaymentNotFoundException(formPaymentId);
			
		}catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(Messages.PAYMENT_IN_USE_METHOD, formPaymentId));
		}
	}
}
