package com.wantfood.aplication.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.wantfood.aplication.WantfoodApiApplication;
import com.wantfood.aplication.domain.model.Restaurante;
import com.wantfood.aplication.domain.repository.RestauranteRepository;

public class ConsultaRestauranteMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(WantfoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository restaurante = applicationContext.getBean(RestauranteRepository .class);
		
		List<Restaurante> todosRestaurantes = restaurante.todas();
		
		todosRestaurantes.forEach(arr -> System.out.println("Nome: " + arr.getNome() +
				", Frete: " + arr.getTaxaFrete() +
				", Nome da cozinha: " + arr.getCozinha().getNome()));

		
	}
}
