package com.wantfood.aplication.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInputDTO {
	
	@NotNull
	private Long produtoId;
	
	@NotNull
	@PositiveOrZero
    private Integer quantidade;
    
    private String observacao;
    
}
