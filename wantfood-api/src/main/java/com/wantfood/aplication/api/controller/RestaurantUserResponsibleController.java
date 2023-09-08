package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.UserDTOAssembler;
import com.wantfood.aplication.api.model.UserDTO;
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
@RequestMapping("/restaurants/{restaurantId}/responsible")
@RequiredArgsConstructor
public class RestaurantUserResponsibleController {
	
    private final RegistrationRestaurantService registerRestaurant;
    
    private final UserDTOAssembler userDTOAssembler;
    
    @GetMapping
    public List<UserDTO> list(@PathVariable Long restaurantId) {
        var restaurant = registerRestaurant.fetchOrFail(restaurantId);
        
        return userDTOAssembler.toCollectionModel(restaurant.getResponsible());
    }
    
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long restaurantId, @PathVariable Long userId) {
        registerRestaurant.disassociateResponsible(restaurantId, userId);
    }
    
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void connect(@PathVariable Long restaurantId, @PathVariable Long userId) {
        registerRestaurant.connectResponsible(restaurantId, userId);
    }
}
