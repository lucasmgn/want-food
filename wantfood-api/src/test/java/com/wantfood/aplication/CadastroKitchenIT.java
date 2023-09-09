package com.wantfood.aplication;

import static org.hamcrest.CoreMatchers.equalTo;

import com.wantfood.aplication.domain.model.Kitchen;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.wantfood.aplication.domain.repository.KitchenRepository;
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
class CadastroKitchenIT {
	
	private static final int COZINHA_ID_INEXISTENTE = 100;

	//Pegando a porta aleatoria para fazer o teste
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	private Kitchen c2;
	
	private int numberKitchens;
	
	private String jsonCorretoKitchenChinesa;
	
	
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
		RestAssured.basePath = "/kitchens";
		
		//Lendo file json
		jsonCorretoKitchenChinesa = ResourceUtils.getContentFromResource("/json/kitchen-chinesa.json");
		
		//Limpando os dados de todas as tabelas do bd
		databaseCleaner.clearTables();
		
		/*
		 * Antes de começar cada teste, retorna o bd fazendo um callback em todos os testes
		 * flyway.migrate();
		 * */
		
		prepararDadosKitchen();
	}
	
	//Fazendo test de api
	@Test
	void deveRetornarStatus200_QuandoConsultarKitchen() {
		//
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value()); //podendo usar (HttpStatus.OK.value());
	}
	
	//Validando o body da resposta http
	@Test
	void deveRetornarAQuantidadeDeKitchens_QuandoConsultarKitchen() {
		
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(numberKitchens));//validando a amount de objeto na resposta
//			.body("name", Matchers.hasItems("Camponesa", "Baiana"));//verificando se tem esses doi 
																	//2 items com esse name
	}
	
	@Test
	void deveRetornarStatus201_QuandocreateKitchen() {
		//
		RestAssured.given()
			.body(jsonCorretoKitchenChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.when()
			.post()
			.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	void deveRetornarRespostaEStatusCorretos_QuandoConsultarKitchenExistente() {
		//Consultando kitchen de ID 2 e se o name dela é baiana
		RestAssured.given()
		.pathParam("kitchenId", c2.getId())
		.accept(ContentType.JSON)
	.when()
		.get("/{kitchenId}")
	.then()
		.statusCode(HttpStatus.OK.value())
		.body("name", equalTo(c2.getName()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarKitchenInexistente() {
		RestAssured.given()
			.pathParam("kitchenId", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{kitchenId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	//Povando a tabela kitchen
	private void prepararDadosKitchen() {
		Kitchen c1 = new Kitchen();
		c1.setName("Camponesa");
		kitchenRepository.save(c1);
		
		c2 = new Kitchen();
		c2.setName("Baiana");
		kitchenRepository.save(c2);
		
//		numberKitchens = (int) kitchenRepository.count();
		numberKitchens = kitchenRepository.findAll().size();
		
	}
}







//Teste de integração 

//Instanciando classe de serviço para add a kitchen teste
//@Autowired
//private KitchenRegistrationService registerKitchenService;
//
//@Test
//void deveAtribuirId_QuandoCadastrarKitchenComDadosCorretos() {
//	//cenário
//	Kitchen kitchen = new Kitchen();
//	kitchen.setName("Italiana");
//	
//	//ação
//	registerKitchenService.add(kitchen);
//	
//	//validação
//	assertThat(kitchen).isNotNull();
//	assertThat(kitchen.getId()).isNotNull();
//	assertThat(kitchen.getId()).isGreaterThan(3);
//}
//
//@Test
//void deveFalhar_QuandoCadastrarKitchenSemname() {
//	Kitchen kitchen = new Kitchen();
//	kitchen.setName(null);
//	
//	 ConstraintViolationException erroEsperado =
//		      Assertions.assertThrows(ConstraintViolationException.class, () -> {
//		    	  registerKitchenService.add(kitchen);
//		      });	 
//	 assertThat(erroEsperado).isNotNull();
//}
//
//@Test
//void deveFalhar_QuandoExcluirKitchenEmUso() {
//	EntityInUseException e = Assertions.assertThrows(EntityInUseException.class, () ->{
//		registerKitchenService.delete(1L);
//	});
//	
//	assertThat(e).isNotNull();	
//}
//
//@Test
//void deveFalhar_QuandoExcluirKitchenInexistente() {
//	EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class, () ->{
//		registerKitchenService.delete(15L);
//	});
//	
//	assertThat(e).isNotNull();
//}