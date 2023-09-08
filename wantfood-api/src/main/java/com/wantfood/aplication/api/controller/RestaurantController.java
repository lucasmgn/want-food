package com.wantfood.aplication.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.wantfood.aplication.api.assembler.RestaurantDTOAssembler;
import com.wantfood.aplication.api.assembler.RestaurantInputDisassembler;
import com.wantfood.aplication.api.model.RestaurantDTO;
import com.wantfood.aplication.api.model.input.RestaurantInputDTO;
import com.wantfood.aplication.api.model.view.RestaurantView;
import com.wantfood.aplication.domain.exception.CityNotFoundException;
import com.wantfood.aplication.domain.exception.GenericException;
import com.wantfood.aplication.domain.exception.KitchenNotFoundException;
import com.wantfood.aplication.domain.exception.RestaurantNotFoundException;
import com.wantfood.aplication.domain.repository.RestaurantRepository;
import com.wantfood.aplication.domain.service.RegistrationRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    private final RegistrationRestaurantService registrationRestaurantService;

    private final RestaurantDTOAssembler restaurantDTOAssembler;

    private final RestaurantInputDisassembler restaurantInputDisassembler;

    //Listando restaurant de forma resumida devido ao @JsonView(RestaurantView.Resume.class)
    @JsonView(RestaurantView.Resume.class)
    @GetMapping
    public List<RestaurantDTO> list() {
        return restaurantDTOAssembler.toCollectionModel(restaurantRepository.findAll());

    }

    @JsonView(RestaurantView.JustName.class)
    @GetMapping(params = "projecao=apenas-name")
    public List<RestaurantDTO> listName() {
        return list();
    }
	
/*
	@GetMapping
	public MappingJacksonValue list(@RequestParam(required = false) String projecao) {
		List<restaurant> restaurants = restaurantRepository.findAll();
		List<restaurantModel> restaurantsModel = restaurantModelAssembler.toCollectionModel(restaurants);
		
		MappingJacksonValue restaurantsWrapper = new MappingJacksonValue(restaurantsModel);
		
		restaurantsWrapper.setSerializationView(RestaurantView.Resume.class);
		
		if ("apenas-name".equals(projecao)) {
			restaurantsWrapper.setSerializationView(RestaurantView.JustName.class);
		} else if ("completo".equals(projecao)) {
			restaurantsWrapper.setSerializationView(null);
		}
		
		return restaurantsWrapper;
	}

*/
	
/*
	Retorna um lista de restaurants, se colocar os paremtros de resumo,
	irá retornar apenas os restaurants resumidos e se utilizar a projeção
	de apenas names, será mostrado apenas os names
	
	@GetMapping
	public List<restaurantModel> list() {
			return restaurantModelAssembler.toCollectionModel(restaurantRepository.findAll());
		}
	
		@JsonView(RestaurantView.Resume.class)
		@GetMapping(params = "projecao=resumo")
		public List<restaurantModel> listResumido() {
			return list();
		}

		@JsonView(RestaurantView.JustName.class)
		@GetMapping(params = "projecao=apenas-name")
		public List<restaurantModel> listJustNames() {
			return list();
	}
	*/

    @GetMapping("/{restaurantId}")
    public RestaurantDTO find(@PathVariable Long restaurantId) {
        var restaurant = registrationRestaurantService.fetchOrFail(restaurantId);

        return restaurantDTOAssembler.toModel(restaurant);
    }

    /*
     * @Valid, antes de chamar o método add ele valida se o a nova entidade
     * de restaurant atende as especificações das colunas
     * @Validated, tem a mesma função que o @Valid porém ele recebe argumentos
     * fazendo a validação usando o group registrationRestaurantServices @Validated(Groups.Cadastrorestaurant.class)
     * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDTO add(@RequestBody @Valid RestaurantInputDTO restaurantInputDTO) {
        try {
            var restaurant = restaurantInputDisassembler.toDomainObject(restaurantInputDTO);

            return restaurantDTOAssembler.toModel(registrationRestaurantService.add(restaurant));

        } catch (KitchenNotFoundException | CityNotFoundException e) {
            throw GenericException.notFound(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public RestaurantDTO atualizar(@PathVariable Long restaurantId,
                                   @RequestBody @Valid RestaurantInputDTO restaurantInputDTO) {

        try {
            var restaurantAtual = registrationRestaurantService.fetchOrFail(restaurantId);

            restaurantInputDisassembler.copyToDomainObject(restaurantInputDTO, restaurantAtual);

            return restaurantDTOAssembler.toModel(registrationRestaurantService.add(restaurantAtual));

        } catch (KitchenNotFoundException | CityNotFoundException e) {
            throw GenericException.notFound(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void active(@PathVariable Long restaurantId) {
        registrationRestaurantService.active(restaurantId);
    }

    @DeleteMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long restaurantId) {
        registrationRestaurantService.deactivate(restaurantId);
    }

    // Irei escolher quais restaurants em massa serão ativados pelo id ex: [1, 2, 3]
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activeAllRestaurantsController(@RequestBody List<Long> restaurantIds) {
        try {
            registrationRestaurantService.activeTodosrestaurants(restaurantIds);
        } catch (RestaurantNotFoundException e) {
            throw GenericException.notFound(e.getMessage());
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeactivateAllRestaurants(@RequestBody List<Long> restaurantIds) {
        try {
            registrationRestaurantService.deactivateAllRestaurants(restaurantIds);
        } catch (RestaurantNotFoundException e) {
            throw GenericException.notFound(e.getMessage());
        }

    }

    @PutMapping("/{restaurantId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void open(@PathVariable Long restaurantId) {
        registrationRestaurantService.open(restaurantId);
    }

    @PutMapping("/{restaurantId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void close(@PathVariable Long restaurantId) {
        registrationRestaurantService.close(restaurantId);
    }

}
