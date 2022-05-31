package com.wantfood.aplication.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.wantfood.aplication.WantfoodApiApplication;
import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.repository.CozinhaRepository;

@Component
public class AlteracaoCozinha {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(WantfoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);
		cozinha.setNome("Italiana");
		
		cadastroCozinha.adicionar(cozinha);
		
	}
}
