package com.wantfood.aplication.api.model.input;

import java.math.BigDecimal;

import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteInputDTO {
	
	@NotBlank
	private String nome;
	
	@NotNull 
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	
	@Valid
	@NotNull
	@ManyToOne
	private CozinhaInputDTO cozinha;
}
