package com.wantfood.aplication.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.exception.RestauranteNaoEncontradoException;
import com.wantfood.aplication.domain.model.Cidade;
import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.model.Restaurante;
import com.wantfood.aplication.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Transactional //todos os metodos public que altereram o bd são anotados com o @Transactional
	public Restaurante adicionar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cadastroCozinhaService.buscaOuFalha(cozinhaId);
		Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);
		
		restaurante.getEndereco().setCidade(cidade);
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		//Ela será salva
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.ativar();
	}
	
	@Transactional
	public void desativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.desativar();
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
}
