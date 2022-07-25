package com.wantfood.aplication.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.exception.PedidoNaoEncontradoException;
import com.wantfood.aplication.domain.model.Cidade;
import com.wantfood.aplication.domain.model.FormaPagamento;
import com.wantfood.aplication.domain.model.Pedido;
import com.wantfood.aplication.domain.model.Produto;
import com.wantfood.aplication.domain.model.Restaurante;
import com.wantfood.aplication.domain.model.Usuario;
import com.wantfood.aplication.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		validarPedido(pedido); //validando o pedido se ele contém todos os requisitos necessários
		validarItens(pedido); //validando os itens
		
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();
		
		return pedidoRepository.save(pedido);
	}

	private void validarItens(Pedido pedido) {
		pedido.getItens().forEach( item -> {
			Produto produto = cadastroProduto.buscaOuFalha(
					pedido.getRestaurante().getId(), item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}

	private void validarPedido(Pedido pedido) {
		
		Cidade cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
		Usuario cliente = cadastroUsuario.buscarOuFalhar(pedido.getCliente().getId());
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscaOuFalha(pedido.getFormaPagamento().getId());
		
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setRestaurante(restaurante);
		pedido.setCliente(cliente);
		pedido.setFormaPagamento(formaPagamento);
	}
	
	public Pedido buscarOuFalhar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido)
			.orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}

}
