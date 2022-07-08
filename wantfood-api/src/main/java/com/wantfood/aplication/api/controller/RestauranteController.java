package com.wantfood.aplication.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wantfood.aplication.api.model.CozinhaDTO;
import com.wantfood.aplication.api.model.RestauranteDTO;
import com.wantfood.aplication.api.model.input.RestauranteInputDTO;
import com.wantfood.aplication.domain.exception.CozinhaNaoEncontradaException;
import com.wantfood.aplication.domain.exception.NegocioException;
import com.wantfood.aplication.domain.model.Cozinha;
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
	
	@GetMapping
	public List<RestauranteDTO> listar(){
		
		return collectionDTO(restauranteRepository.findAll());
	}
	
	@GetMapping("/{restauranteId}") 
	public RestauranteDTO buscar(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		return toDTO(restaurante);	
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
	    	Restaurante restaurante = toDomainObject(restauranteInputDTO);
	        return toDTO(cadastroRestauranteService.adicionar(restaurante));
	    } catch (CozinhaNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage());
	    }
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInputDTO restauranteInputDTO){
		
		try {
			Restaurante restaurante = toDomainObject(restauranteInputDTO);
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
			
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento",  "endereco", "dataCadastro", "produtos");
		
			return toDTO(cadastroRestauranteService.adicionar(restauranteAtual));	
			
		}catch(CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}	
	}
	
	private RestauranteDTO toDTO(Restaurante restaurante) {
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		
		RestauranteDTO dto = new RestauranteDTO();
		dto.setId(restaurante.getId());
		dto.setNome(restaurante.getNome());
		dto.setTaxaFrete(restaurante.getTaxaFrete());
		dto.setCozinha(cozinhaDTO);
		return dto;
	}
	
	//Criando uma coleção de dto
	private List<RestauranteDTO> collectionDTO(List<Restaurante> restaurantes){
		
		return restaurantes.stream()
				.map(restaurante -> toDTO(restaurante))
				.collect(Collectors.toList());
	}	
	
	//Transformando o InputDTO em um restaurante
	private Restaurante toDomainObject(RestauranteInputDTO restauranteInputDTO) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInputDTO.getNome());
		restaurante.setTaxaFrete(restauranteInputDTO.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInputDTO.getCozinha().getId());
		
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}
}
