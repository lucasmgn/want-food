package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.FormPaymentDTOAssembler;
import com.wantfood.aplication.api.model.FormPaymentDTO;
import com.wantfood.aplication.domain.service.RegistrationRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/formas-pagamento")
@RequiredArgsConstructor
public class RestaurantFormPaymentController {
	
	private final RegistrationRestaurantService registrationRestaurantService;
	
	private final FormPaymentDTOAssembler formPaymentDTOAssembler;
	
	@GetMapping
	public List<FormPaymentDTO> list(@PathVariable Long restaurantId){
		var restaurant = registrationRestaurantService.fetchOrFail(restaurantId);
		return formPaymentDTOAssembler.toCollectionModel(restaurant.getPaymentMethods());
	}
	
	@DeleteMapping("/{formPaymentId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociate(@PathVariable Long restaurantId, @PathVariable Long formPaymentId) {
		registrationRestaurantService.disassociateFormPayment(restaurantId, formPaymentId);
	}
	
	@PutMapping("/{formPaymentId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void connect(@PathVariable Long restaurantId, @PathVariable Long formPaymentId) {
		registrationRestaurantService.associateFormPayment(restaurantId, formPaymentId);
	}
}