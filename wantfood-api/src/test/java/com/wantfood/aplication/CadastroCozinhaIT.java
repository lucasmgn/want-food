package com.wantfood.aplication;

import static org.hamcrest.CoreMatchers.equalToObject;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.repository.CozinhaRepository;
import com.wantfood.aplication.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/*
 * webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
 * tomcat irá levantar a aplicação em uma porta aleatoria
 * colocando o IT por causa da dependencia io.rest-assured
 * @TestPropertySource(), usado para configurar e usar o application-teste.properties
 * */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {
	
	//Pegando a porta aleatoria para fazer o teste
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	/*
	 * criando instância de flayway para acessar o migrate e não ter erros de versão do bd
	 * na hora que executar os testes
	 * @Autowired
	 * private Flyway flyway;
	 * */

	
	
	@BeforeEach //anotação att do junit 5
	void setup() {
		//mostrando no console as validações que falharam
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		//Limpando os dados de todas as tabelas do bd
		databaseCleaner.clearTables();
		
		/*
		 * Antes de começar cada teste, retorna o bd fazendo um callback em todos os testes
		 * flyway.migrate();
		 * */
		
		prepararDadosCozinha();
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
	void deveConter2Cozinhas_QuandoConsultarCozinha() {
		//
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(2));//validando se vão aparecer 4 objetos na resposta
//			.body("nome", Matchers.hasItems("Camponesa", "Baiana"));//verificando se tem 2 itens com esse nome
	}
	
	@Test
	void deveRetornarStatus201_QuandoCriarCozinha() {
		//
		RestAssured.given()
			.body("{\"nome\": \"Chinesa\"}")
			.contentType(ContentType.JSON)
			.when()
			.post()
			.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		//Consultando cozinha de ID 2 e se o nome dela é baiana
		RestAssured.given()
		.pathParam("cozinhaId", 2)
		.accept(ContentType.JSON)
	.when()
		.get("/{cozinhaId}")
	.then()
		.statusCode(HttpStatus.OK.value())
		.body("nome", equalToObject("Baiana"));
	}
	
	//Povando a tabela cozinha
	private void prepararDadosCozinha() {
		Cozinha c1 = new Cozinha();
		c1.setNome("Camponesa");
		cozinhaRepository.save(c1);
		
		Cozinha c2 = new Cozinha();
		c2.setNome("Baiana");
		cozinhaRepository.save(c2);
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