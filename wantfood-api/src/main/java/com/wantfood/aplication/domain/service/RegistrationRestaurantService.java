package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.exception.RestaurantNotFoundException;
import com.wantfood.aplication.domain.model.Restaurant;
import com.wantfood.aplication.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationRestaurantService {

	private final RestaurantRepository restaurantRepository;
	
	private final KitchenRegistrationService registerKitchenService;
	
	private final CityRegistrationService cityRegistrationService;
	
	private final RegistrationFormPaymentService registrationFormPaymentService;
	
	private final ServiceUserRegistration serviceUserRegistration;
	
	@Transactional //todos os metodos public que altereram o bd são anotados com o @Transactional
	public Restaurant add(Restaurant restaurant) {
		var kitchenId = restaurant.getKitchen().getId();
		var cityId = restaurant.getAddress().getCity().getId();
		
		var kitchen = registerKitchenService.fetchOrFail(kitchenId);
		var city = cityRegistrationService.fetchOrFail(cityId);
		
		restaurant.getAddress().setCity(city);
		restaurant.setKitchen(kitchen);
		
		return restaurantRepository.save(restaurant);
	}
	
	//ativando restaurant
	@Transactional
	public void active(Long restaurantId) {
		//Ela será salva
		var restaurantAtual = fetchOrFail(restaurantId);
		restaurantAtual.active();
	}
	
	//desativando restaurant
	@Transactional
	public void deactivate(Long restaurantId) {
		var restaurantAtual = fetchOrFail(restaurantId);
		restaurantAtual.deactivate();
	}

	
	@Transactional //para colocar todas as operações de transação em massa, não ter nenhuma inconsistência
	public void activeAllRestaurants(List<Long> restaurantIds) {
		//chamando o metodo active para a lista restaurantsIds
	    restaurantIds.forEach(this::active);
	}
	
	@Transactional 
	public void deactivateAllRestaurants(List<Long> restaurantIds) {
	    restaurantIds.forEach(this::deactivate);
	}
	
	//desvinculando forma de pagamento do restaurant
	@Transactional
	public void disassociateFormPayment(Long restaurantId, Long formPaymentId) {
		var restaurant = fetchOrFail(restaurantId);
		var formPayment = registrationFormPaymentService.fetchOrFail(formPaymentId);
		
		restaurant.removeFormPayment(formPayment);
		//não precisa do metodo save pq a Anotação transactional gerencia e salva essa boa alteração
	}
	
	@Transactional
	public void associateFormPayment(Long restaurantId, Long formPaymentId) {
		var restaurant = fetchOrFail(restaurantId);
		var formPayment = registrationFormPaymentService.fetchOrFail(formPaymentId);
		
		restaurant.addFormPayment(formPayment);
		//não precisa do metodo save pq a Anotação transactional gerencia e salva essa boa alteração
	}
	
	public Restaurant fetchOrFail(Long restaurantId) {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}
	
	@Transactional
	public void open(Long restaurantId) {
	    var restaurantAtual = fetchOrFail(restaurantId);
	    
	    restaurantAtual.open();
	}

	@Transactional
	public void close(Long restaurantId) {
	    var restaurantAtual = fetchOrFail(restaurantId);
	    
	    restaurantAtual.close();
	}
	
	@Transactional
	public void disassociateResponsible(Long restaurantId, Long userId) {
	    var restaurant = fetchOrFail(restaurantId);
	    var user = serviceUserRegistration.fetchOrFail(userId);
	    
	    restaurant.removeResponsible(user);
	}

	@Transactional
	public void connectResponsible(Long restaurantId, Long userId) {
	    var restaurant = fetchOrFail(restaurantId);
	    var user = serviceUserRegistration.fetchOrFail(userId);
	    
	    restaurant.addResponsible(user);
	}
}
