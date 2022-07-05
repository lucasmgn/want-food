package com.wantfood.aplication;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/*
 * webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
 * tomcat irá levantar a aplicação em uma porta aleatoria
 * */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {
	
	//Pegando a porta aleatoria para fazer o teste
	@LocalServerPort
	private int port;
	
	@BeforeEach //anotação att do junit 5
	void setup() {
		//mostrando no console as validações que falharam
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	}
	
	//Fazendo test de api
	@Test
	void deveRetornarStatus200_QuandoConsultarCozinha() {
		//
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value()); //podendo usar (HttpStatus.OK.value());
	}
	
	//Validando o corpo da resposta http
	@Test
	void deveConter4Cozinhas_QuandoConsultarCozinha() {
		//
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(4))//validando se vão aparecer 4 objetos na resposta
			.body("nome", Matchers.hasItems("Indiana", "Tailandesa"));//verificando se tem 2 itens com esse nome
	}
}







//Teste de integração 

//Instanciando classe de serviço para adicionar a cozinha teste
//@Autowired
//private CadastroCozinhaService cadastroCozinhaService;
//
//@Test
//void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
//	//cenário
//	Cozinha cozinha = new Cozinha();
//	cozinha.setNome("Italiana");
//	
//	//ação
//	cadastroCozinhaService.adicionar(cozinha);
//	
//	//validação
//	assertThat(cozinha).isNotNull();
//	assertThat(cozinha.getId()).isNotNull();
//	assertThat(cozinha.getId()).isGreaterThan(3);
//}
//
//@Test
//void deveFalhar_QuandoCadastrarCozinhaSemNome() {
//	Cozinha cozinha = new Cozinha();
//	cozinha.setNome(null);
//	
//	 ConstraintViolationException erroEsperado =
//		      Assertions.assertThrows(ConstraintViolationException.class, () -> {
//		    	  cadastroCozinhaService.adicionar(cozinha);
//		      });	 
//	 assertThat(erroEsperado).isNotNull();
//}
//
//@Test
//void deveFalhar_QuandoExcluirCozinhaEmUso() {
//	EntidadeEmUsoException e = Assertions.assertThrows(EntidadeEmUsoException.class, () ->{
//		cadastroCozinhaService.excluir(1L);
//	});
//	
//	assertThat(e).isNotNull();	
//}
//
//@Test
//void deveFalhar_QuandoExcluirCozinhaInexistente() {
//	EntidadeNaoEncontradaException e = Assertions.assertThrows(EntidadeNaoEncontradaException.class, () ->{
//		cadastroCozinhaService.excluir(15L);
//	});
//	
//	assertThat(e).isNotNull();
//}