package com.wantfood.aplication.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.exception.RestauranteNaoEncontradoException;
import com.wantfood.aplication.domain.model.Cidade;
import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.model.FormaPagamento;
import com.wantfood.aplication.domain.model.Restaurante;
import com.wantfood.aplication.domain.model.Usuario;
import com.wantfood.aplication.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
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
	
	//ativando restaurante
	@Transactional
	public void ativar(Long restauranteId) {
		//Ela será salva
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.ativar();
	}
	
	//desativando restaurante
	@Transactional
	public void desativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.desativar();
	}

	
	@Transactional //para colocar todas as operações de transação em massa, não ter nenhuma inconsistência
	public void ativarTodosRestaurantes(List<Long> restauranteIds) {
		//chamando o metodo ativar para a lista restaurantesIds
	    restauranteIds.forEach(this::ativar);
	}
	
	@Transactional 
	public void desativarTodosRestaurantes(List<Long> restauranteIds) {
	    restauranteIds.forEach(this::desativar);
	}
	
	//desvinculando forma de pagamento do restaurante
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscaOuFalha(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
		//não precisa do metodo save pq a Anotação transactional gerencia e salva essa boa alteração
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscaOuFalha(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
		//não precisa do metodo save pq a Anotação transactional gerencia e salva essa boa alteração
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
	@Transactional
	public void abrir(Long restauranteId) {
	    Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	    
	    restauranteAtual.abrir();
	}

	@Transactional
	public void fechar(Long restauranteId) {
	    Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	    
	    restauranteAtual.fechar();
	}
	
	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
	    Restaurante restaurante = buscarOuFalhar(restauranteId);
	    Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
	    
	    restaurante.removerResponsavel(usuario);
	}

	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
	    Restaurante restaurante = buscarOuFalhar(restauranteId);
	    Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
	    
	    restaurante.adicionarResponsavel(usuario);
	}
	
	
	
}
