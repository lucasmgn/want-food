package com.wantfood.aplication.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaInputDTO {
	
	@ApiModelProperty(example = "Brasileira", required = true)
	@NotBlank
	private String nome;
}
