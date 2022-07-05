package com.wantfood.aplication;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.service.CadastroCozinhaService;

@SpringBootTest
class CadastroCozinhaIntegrationTests {

	//Instanciando classe de serviço para adicionar a cozinha teste
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Test
	void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
		//cenário
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Italiana");
		
		//ação
		cadastroCozinhaService.adicionar(cozinha);
		
		//validação
		assertThat(cozinha).isNotNull();
		assertThat(cozinha.getId()).isNotNull();
		assertThat(cozinha.getId()).isGreaterThan(3);
	}
	
	@Test
	void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);
		
		 ConstraintViolationException erroEsperado =
			      Assertions.assertThrows(ConstraintViolationException.class, () -> {
			    	  cadastroCozinhaService.adicionar(cozinha);
			      });
		 
		 assertThat(erroEsperado).isNotNull();
	}

}
