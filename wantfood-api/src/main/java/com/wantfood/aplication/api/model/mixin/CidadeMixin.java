package com.wantfood.aplication.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wantfood.aplication.domain.model.Estado;

public abstract class CidadeMixin {
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;
}
