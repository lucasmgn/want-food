package com.wantfood.aplication.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CityInputDTO {
	
	@ApiModelProperty(example = "Salvador", required = true)
	@NotBlank
	private String name;
	
	@Valid
	@NotNull
	private StateIdInputDTO state;
}
