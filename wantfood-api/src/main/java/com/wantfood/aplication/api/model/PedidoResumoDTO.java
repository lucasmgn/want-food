package com.wantfood.aplication.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoDTO {
	
	private String codigo; 
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;	
	private BigDecimal valorTotal;
	private String status;
	private OffsetDateTime dataCriacao; 
	private RestauranteResumoDTO restaurante;
	private UsuarioDTO cliente;
}
