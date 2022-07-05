package com.wantfood.aplication.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wantfood.aplication.core.validation.ValidacaoException;
import com.wantfood.aplication.domain.exception.CozinhaNaoEncontradaException;
import com.wantfood.aplication.domain.exception.NegocioException;
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
	private SmartValidator validator;
	
	@GetMapping
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId){
		return cadastroRestauranteService.buscarOuFalhar(restauranteId);		
	}
	
	/*
	 * @Valid, antes de chamar o método adicionar ele valida se o a nova entidade 
	 * de restaurante atende as especificações das colunas 
	 * @Validated, tem a mesma função que o @Valid porém ele recebe argumentos 
	 * fazendo a validação usando o grupo cadastroRestaurantes @Validated(Groups.CadastroRestaurante.class)
	 * */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(
			@RequestBody @Valid Restaurante restaurante){
	    try {
	        return cadastroRestauranteService.salvar(restaurante);
	    } catch (CozinhaNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage());
	    }
	}
	
	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid Restaurante restaurante){
		
		try {
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
			
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento",  "endereco", "dataCadastro", "produtos");
		
			return cadastroRestauranteService.salvar(restauranteAtual);	
			
		}catch(CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}	
	}
	
	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos, HttpServletRequest request){
		
		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		merge(campos, restauranteAtual, request);
		
		validacao(restauranteAtual, "restaurante");
		
		return atualizar(restauranteId, restauranteAtual);	
	}

	private void validacao(Restaurante restauranteAtual, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restauranteAtual, objectName);
		validator.validate(restauranteAtual, bindingResult);
		
		//verifcando se tem erros no bidingResult
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
			HttpServletRequest request) {
		
		//Cirando variável para passar como parametro da função HttpMessageNotReadableException
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		/*
		 * Pegando a Exception IllegalArgumentException para tratar ela com a handlePropertyBindingException
		 * */
		try {
			//Convertendo os campos da Restaurante para serem atribuidos de maneira certa pelo Map
			ObjectMapper objectMapper = new ObjectMapper();
			
			//Configurando o ObjectMapper, irá falhar quando alguma propriedade for ignorada
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			
			//Configurando o ObjectMapper, irá falhar quando alguma propriedade não existir(é o padrao)
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true); //acessando o atributo nome da classe restaurante
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		}catch(IllegalArgumentException e) {
			//Pegando a causa de exception com o commons.lang3
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);

		}
	}	
}
