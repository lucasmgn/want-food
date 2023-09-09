package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.api.model.RestaurantDTO;
import com.wantfood.aplication.api.model.input.RestaurantInputDTO;

import java.util.List;

public interface RestauranteService {

    List<RestaurantDTO> findAll();

    RestaurantDTO findBy(Long restaurantId);

    RestaurantDTO create(RestaurantInputDTO restaurantInputDTO);

    RestaurantDTO update(Long restaurantId, RestaurantInputDTO restaurantInputDTO);

    void active(Long restaurantId);

    void deactivate(Long restaurantId);

    void activeAllRestaurants(List<Long> restaurantIds);
    void deactivateAllRestaurants(List<Long> restaurantIds);

    void open(Long restaurantId);

    void close(Long restaurantId);
}
