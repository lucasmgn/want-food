package com.wantfood.aplication.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//Classe criada para representar as vendas 
@AllArgsConstructor //criando construtures 
@Getter
@Setter
public class VendaDiaria {
		
	private Date data;
	private Long totalVendas;
	private BigDecimal totalFaturado;
}
