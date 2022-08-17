package com.wantfood.aplication.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fasterxml.jackson.annotation.JsonView;
import com.wantfood.aplication.api.assembler.RestauranteDTOAssembler;
import com.wantfood.aplication.api.assembler.RestauranteInputDisassembler;
import com.wantfood.aplication.api.model.RestauranteDTO;
import com.wantfood.aplication.api.model.input.RestauranteInputDTO;
import com.wantfood.aplication.api.model.view.RestauranteView;
import com.wantfood.aplication.domain.exception.CidadeNaoEncontradaException;
import com.wantfood.aplication.domain.exception.CozinhaNaoEncontradaException;
import com.wantfood.aplication.domain.exception.NegocioException;
import com.wantfood.aplication.domain.exception.RestauranteNaoEncontradoException;
import com.wantfood.aplication.domain.model.Restaurante;
import com.wantfood.aplication.domain.repository.RestauranteRepository;
import com.wantfood.aplication.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private RestauranteDTOAssembler restauranteDTOAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	//Listando restaurante de forma resumida devido ao @JsonView(RestauranteView.Resumo.class)
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public List<RestauranteDTO> listar(){
		return restauranteDTOAssembler.toCollectionModel(restauranteRepository.findAll());
		
	}
	
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteDTO> listarNome(){
		return restauranteDTOAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);
//		
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesModel);
//		
//		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//		
//		if ("apenas-nome".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		} else if ("completo".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(null);
//		}
//		
//		return restaurantesWrapper;
//	}
	
	
//	Retorna um lista de restaurantes, se colocar os paremtros de resumo,
//	irá retornar apenas os restaurantes resumidos e se utilizar a projeção
//	de apenas nomes, será mostrado apenas os nomes
	
//	@GetMapping
//	public List<RestauranteModel> listar() {
//			return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
//		}
//	
//		@JsonView(RestauranteView.Resumo.class)
//		@GetMapping(params = "projecao=resumo")
//		public List<RestauranteModel> listarResumido() {
//			return listar();
//		}
//
//		@JsonView(RestauranteView.ApenasNome.class)
//		@GetMapping(params = "projecao=apenas-nome")
//		public List<RestauranteModel> listarApenasNomes() {
//			return listar();
//	}
	
	@GetMapping("/{restauranteId}") 
	public RestauranteDTO buscar(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		return restauranteDTOAssembler.toModel(restaurante);	
	}
	
	/*
	 * @Valid, antes de chamar o método adicionar ele valida se o a nova entidade 
	 * de restaurante atende as especificações das colunas 
	 * @Validated, tem a mesma função que o @Valid porém ele recebe argumentos 
	 * fazendo a validação usando o grupo cadastroRestaurantes @Validated(Groups.CadastroRestaurante.class)
	 * */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInputDTO restauranteInputDTO){
	    try {
	    	Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInputDTO);
	    	
	    	return restauranteDTOAssembler.toModel(cadastroRestauranteService.adicionar(restaurante));
	    	
	    } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage());
	    }
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInputDTO restauranteInputDTO){
		
		try {
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
			
			restauranteInputDisassembler.copyToDomainObject(restauranteInputDTO, restauranteAtual);
			
			return restauranteDTOAssembler.toModel(cadastroRestauranteService.adicionar(restauranteAtual));
	
		}catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}	
	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.desativar(restauranteId);
	}
	
	// Irei escolher quais restaurantes em massa serão ativados pelo id ex: [1, 2, 3]
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarTodosRestaurantesController(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestauranteService.ativarTodosRestaurantes(restauranteIds);
		}catch(RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativarTodosRestaurantesController(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestauranteService.desativarTodosRestaurantes(restauranteIds);
		}catch(RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}
	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
	    cadastroRestauranteService.abrir(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
	    cadastroRestauranteService.fechar(restauranteId);
	} 
	
}
