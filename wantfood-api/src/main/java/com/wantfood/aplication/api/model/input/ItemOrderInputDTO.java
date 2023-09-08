package com.wantfood.aplication.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemOrderInputDTO {
	
	@NotNull
	private Long productId;
	
	@NotNull
	@PositiveOrZero
    private Integer amount;
    
    private String observation;
    
}
