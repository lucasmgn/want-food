package com.wantfood.aplication.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wantfood.aplication.api.model.mixin.CidadeMixin;
import com.wantfood.aplication.api.model.mixin.CozinhaMixin;
import com.wantfood.aplication.api.model.mixin.RestauranteMixin;
import com.wantfood.aplication.domain.model.Cidade;
import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.model.Restaurante;

//extendendo o SimpleModule para registrar os mixin que estou utilizando 
@Component
public class JacksonMixinModule extends SimpleModule{
	
	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		//Ligando a classe Restaurante com a classe mixin
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}

}
