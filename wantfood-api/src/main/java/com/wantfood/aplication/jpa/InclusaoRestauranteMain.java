package com.wantfood.aplication.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.wantfood.aplication.WantfoodApiApplication;
import com.wantfood.aplication.domain.model.Restaurante;
import com.wantfood.aplication.domain.repository.RestauranteRepository;

public class InclusaoRestauranteMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(WantfoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository cadastroRestaurante = applicationContext.getBean(RestauranteRepository.class);
		
		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("BabarFoood");
		
		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Letreino de uvas");
		
		cadastroRestaurante.adicionar(restaurante1);
		cadastroRestaurante.adicionar(restaurante2);
		
	}
}
