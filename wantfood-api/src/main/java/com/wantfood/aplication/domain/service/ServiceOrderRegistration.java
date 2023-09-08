package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.exception.OrderNotFoundException;
import com.wantfood.aplication.domain.model.Order;
import com.wantfood.aplication.domain.model.Product;
import com.wantfood.aplication.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ServiceOrderRegistration {
	
	private final OrderRepository orderRepository;
	
	private final RegistrationRestaurantService registrationRestaurantService;

	private final CityRegistrationService cityRegistrationService;

	private final ServiceUserRegistration serviceUserRegistration;

	private final ProductRegistrationService productRegistrationService;

	private final RegistrationFormPaymentService registrationFormPaymentService;
	
	@Transactional
	public Order emitir(Order order) {
		validateOrder(order); //validando o order se ele contém todos os requisitos necessários
		validateItems(order); //validando os items
		
		order.setRateShipping(order.getRestaurant().getRateShipping());
		order.calcularamount();
		
		return orderRepository.save(order);
	}

	private void validateItems(Order order) {
		order.getItems().forEach(item -> {
			Product product = productRegistrationService.fetchOrFail(
					order.getRestaurant().getId(), item.getProduct().getId());
			
			item.setOrder(order);
			item.setProduct(product);
			item.setUnitPrice(product.getPrice());
		});
	}

	private void validateOrder(Order order) {
		
		var city = cityRegistrationService.fetchOrFail(order.getAddressDelivery().getCity().getId());
		var restaurant = registrationRestaurantService.fetchOrFail(order.getRestaurant().getId());
		var client = serviceUserRegistration.fetchOrFail(order.getClient().getId());
		var formPayment = registrationFormPaymentService.fetchOrFail(order.getFormPayment().getId());
		
		order.getAddressDelivery().setCity(city);
		order.setRestaurant(restaurant);
		order.setClient(client);
		order.setFormPayment(formPayment);
	}
	
	public Order fetchOrFail(String codeOrder) {
		return orderRepository.findBycode(codeOrder)
			.orElseThrow(() -> new OrderNotFoundException(codeOrder));
	}
}
