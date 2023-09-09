package com.wantfood.aplication.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.wantfood.aplication.api.model.RestaurantDTO;
import com.wantfood.aplication.api.model.input.RestaurantInputDTO;
import com.wantfood.aplication.api.model.view.RestaurantView;
import com.wantfood.aplication.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestauranteService restauranteService;

    @JsonView(RestaurantView.Resume.class)
    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> listAll() {
        return ResponseEntity.ok(restauranteService.findAll());
    }

    @JsonView(RestaurantView.JustName.class)
    @GetMapping(params = "projecao=apenas-name")
    public ResponseEntity<List<RestaurantDTO>> listName() {
        return listAll();
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> find(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restauranteService.findBy(restaurantId));
    }

    /*
     * @Valid, antes de chamar o método add ele valida se o a nova entidade
     * de restaurant atende as especificações das colunas
     * @Validated, tem a mesma função que o @Valid porém ele recebe argumentos
     * fazendo a validação usando o group registrationRestaurantServices @Validated(Groups.Cadastrorestaurant.class)
     * */
    @PostMapping
    public ResponseEntity<RestaurantDTO> add(@RequestBody @Valid RestaurantInputDTO restaurantInputDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.create(restaurantInputDTO));
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> update(@PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantInputDTO restaurantInputDTO) {
        return ResponseEntity.ok(restauranteService.update(restaurantId, restaurantInputDTO));
    }

    @PutMapping("/{restaurantId}/active")
    public ResponseEntity<Void> active(@PathVariable Long restaurantId) {
        restauranteService.active(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{restaurantId}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long restaurantId) {
        restauranteService.deactivate(restaurantId);
        return ResponseEntity.noContent().build();
    }

    // Irei escolher quais restaurants em massa serão ativados pelo id ex: [1, 2, 3]
    @PutMapping("/activations")
    public ResponseEntity<Void> activeAllRestaurantsController(@RequestBody List<Long> restaurantIds) {
        restauranteService.activeAllRestaurants(restaurantIds);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/activations")
    public ResponseEntity<Void> deactivateAllRestaurants(@RequestBody List<Long> restaurantIds) {
        restauranteService.deactivateAllRestaurants(restaurantIds);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restaurantId}/opening")
    public ResponseEntity<Void> open(@PathVariable Long restaurantId) {
        restauranteService.open(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restaurantId}/closure")
    public ResponseEntity<Void> close(@PathVariable Long restaurantId) {
        restauranteService.close(restaurantId);
        return ResponseEntity.noContent().build();
    }
}
