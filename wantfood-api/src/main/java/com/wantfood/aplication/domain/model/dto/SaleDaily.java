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
public class SaleDaily {
		
	private Date date;
	private Long totalSales;
	private BigDecimal totalInvoiced;
}
