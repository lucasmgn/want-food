package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.api.assembler.RestaurantDTOAssembler;
import com.wantfood.aplication.api.assembler.RestaurantInputDisassembler;
import com.wantfood.aplication.api.model.RestaurantDTO;
import com.wantfood.aplication.api.model.input.RestaurantInputDTO;
import com.wantfood.aplication.domain.exception.CityNotFoundException;
import com.wantfood.aplication.domain.exception.GenericException;
import com.wantfood.aplication.domain.exception.KitchenNotFoundException;
import com.wantfood.aplication.domain.exception.RestaurantNotFoundException;
import com.wantfood.aplication.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    private final RestaurantRepository restaurantRepository;

    private final RegistrationRestaurantService registrationRestaurantService;

    private final RestaurantDTOAssembler restaurantDTOAssembler;

    private final RestaurantInputDisassembler restaurantInputDisassembler;

    @Override
    public List<RestaurantDTO> findAll() {
        return restaurantDTOAssembler.toCollectionModel(restaurantRepository.findAll());
    }

    @Override
    public RestaurantDTO findBy(Long restaurantId) {
        var restaurant = registrationRestaurantService.fetchOrFail(restaurantId);

        return restaurantDTOAssembler.toModel(restaurant);
    }

    @Override
    public RestaurantDTO create(RestaurantInputDTO restaurantInputDTO) {
        try {
            var restaurant = restaurantInputDisassembler.toDomainObject(restaurantInputDTO);

            return restaurantDTOAssembler.toModel(registrationRestaurantService.add(restaurant));

        } catch (KitchenNotFoundException | CityNotFoundException e) {
            throw GenericException.notFound(e.getMessage());
        }
    }

    @Override
    public RestaurantDTO update(Long restaurantId, RestaurantInputDTO restaurantInputDTO) {
        try {
            var restaurantAtual = registrationRestaurantService.fetchOrFail(restaurantId);
            restaurantInputDisassembler.copyToDomainObject(restaurantInputDTO, restaurantAtual);
            return restaurantDTOAssembler.toModel(registrationRestaurantService.add(restaurantAtual));

        } catch (KitchenNotFoundException | CityNotFoundException e) {
            throw GenericException.notFound(e.getMessage());
        }
    }

    @Override
    public void active(Long restaurantId) {
        registrationRestaurantService.active(restaurantId);
    }

    @Override
    public void deactivate(Long restaurantId) {
        registrationRestaurantService.deactivate(restaurantId);
    }

    @Override
    public void activeAllRestaurants(List<Long> restaurantIds) {
        try {
            registrationRestaurantService.activeAllRestaurants(restaurantIds);
        } catch (RestaurantNotFoundException e) {
            throw GenericException.notFound(e.getMessage());
        }
    }

    @Override
    public void deactivateAllRestaurants(List<Long> restaurantIds) {
        try {
            registrationRestaurantService.deactivateAllRestaurants(restaurantIds);
        } catch (RestaurantNotFoundException e) {
            throw GenericException.notFound(e.getMessage());
        }
    }

    @Override
    public void open(Long restaurantId) {
        registrationRestaurantService.open(restaurantId);
    }

    @Override
    public void close(Long restaurantId) {
        registrationRestaurantService.close(restaurantId);
    }
}
