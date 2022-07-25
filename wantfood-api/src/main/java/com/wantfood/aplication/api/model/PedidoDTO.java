package com.wantfood.aplication.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {
	
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;	
	private BigDecimal valorTotal;
    private EnderecoDTO enderecoEntrega;
	private String status;
	private OffsetDateTime dataCriacao; 
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	private FormaPagamentoDTO formaPagamento;
	private RestauranteResumoDTO restaurante;
	private UsuarioDTO cliente;
	private List<ItemPedidoDTO> itens; 
}
