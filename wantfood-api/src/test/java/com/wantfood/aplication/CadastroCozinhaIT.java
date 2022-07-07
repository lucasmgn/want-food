package com.wantfood.aplication;

import static org.hamcrest.CoreMatchers.equalTo;

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
import com.wantfood.aplication.util.ResourceUtils;

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
	
	private static final int COZINHA_ID_INEXISTENTE = 100;

	//Pegando a porta aleatoria para fazer o teste
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private Cozinha c2;
	
	private int numeroCozinhas;
	
	private String jsonCorretoCozinhaChinesa;
	
	
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
		
		//Lendo arquivo json
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource("/json/cozinha-chinesa.json");
		
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
	void deveRetornarAQuantidadeDeCozinhas_QuandoConsultarCozinha() {
		
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(numeroCozinhas));//validando a quantidade de objeto na resposta
//			.body("nome", Matchers.hasItems("Camponesa", "Baiana"));//verificando se tem esses doi 
																	//2 itens com esse nome
	}
	
	@Test
	void deveRetornarStatus201_QuandoCriarCozinha() {
		//
		RestAssured.given()
			.body(jsonCorretoCozinhaChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.when()
			.post()
			.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		//Consultando cozinha de ID 2 e se o nome dela é baiana
		RestAssured.given()
		.pathParam("cozinhaId", c2.getId())
		.accept(ContentType.JSON)
	.when()
		.get("/{cozinhaId}")
	.then()
		.statusCode(HttpStatus.OK.value())
		.body("nome", equalTo(c2.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		RestAssured.given()
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	//Povando a tabela cozinha
	private void prepararDadosCozinha() {
		Cozinha c1 = new Cozinha();
		c1.setNome("Camponesa");
		cozinhaRepository.save(c1);
		
		c2 = new Cozinha();
		c2.setNome("Baiana");
		cozinhaRepository.save(c2);
		
//		numeroCozinhas = (int) cozinhaRepository.count();
		numeroCozinhas = cozinhaRepository.findAll().size();
		
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