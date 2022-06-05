package com.wantfood.aplication.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.exception.EntidadeNaoEncontradaException;
import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.model.Restaurante;
import com.wantfood.aplication.domain.repository.CozinhaRepository;
import com.wantfood.aplication.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository repository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.porId(cozinhaId);
		
		if(cozinha == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com o código %d.", cozinhaId));
		}
		restaurante.setCozinha(cozinha);
		return repository.adicionar(restaurante);
	}
	
}
