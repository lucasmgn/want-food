package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.constants.Messages;
import com.wantfood.aplication.domain.exception.EntityInUseException;
import com.wantfood.aplication.domain.exception.FormPaymentNotFoundException;
import com.wantfood.aplication.domain.exception.ProductNotFoundException;
import com.wantfood.aplication.domain.model.Product;
import com.wantfood.aplication.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProductRegistrationService {
	
	private final ProductRepository productRepository;
	
	@Transactional
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	public Product fetchOrFail(Long restaurantId, Long productId) {
		return productRepository.findById(restaurantId, productId)
				.orElseThrow(() -> new ProductNotFoundException(productId, restaurantId));
	}
	
	@Transactional
	public void delete(Long productId) {
		try {
			productRepository.deleteById(productId);
			productRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
			throw  new FormPaymentNotFoundException(productId);
			
		}catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(Messages.PRODUCT_IN_USE, productId));
		}
	}
}
