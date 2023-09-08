package com.wantfood.aplication;

import com.wantfood.aplication.domain.model.Kitchen;
import com.wantfood.aplication.domain.model.Restaurant;
import com.wantfood.aplication.domain.repository.KitchenRepository;
import com.wantfood.aplication.domain.repository.RestaurantRepository;
import com.wantfood.aplication.util.DatabaseCleaner;
import com.wantfood.aplication.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestaurantITRegistration {
	 private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";

	    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

	    private static final int restaurant_ID_INEXISTENTE = 100;

	    @LocalServerPort
	    private int port;
	    
	    @Autowired
	    private DatabaseCleaner databaseCleaner;
	    
	    @Autowired
	    private KitchenRepository kitchenRepository;
	    
	    @Autowired
	    private RestaurantRepository restaurantRepository;
	    
	    private String jsonrestaurantCorreto;
	    private String jsonrestaurantSemFrete;
	    private String jsonrestaurantSemKitchen;
	    private String jsonrestaurantComKitchenInexistente;
	    
	    private Restaurant burgerToprestaurant;
	    
	    @BeforeEach
	    public void setUp() {
	        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	        RestAssured.port = port;
	        RestAssured.basePath = "/restaurants";

	        jsonrestaurantCorreto = ResourceUtils.getContentFromResource(
	                "/json/restaurant-new-york-barbecue.json");
	        
	        jsonrestaurantSemFrete = ResourceUtils.getContentFromResource(
	                "/json/restaurant-new-york-barbecue-sem-frete.json");
	        
	        jsonrestaurantSemKitchen = ResourceUtils.getContentFromResource(
	                "/json/restaurant-new-york-barbecue-sem-kitchen.json");
	        
	        jsonrestaurantComKitchenInexistente = ResourceUtils.getContentFromResource(
	                "/json/restaurant-new-york-barbecue-com-kitchen-inexistente.json");
	        
	        databaseCleaner.clearTables();
	        prepararDados();
	    }
	    
	    @Test
	    public void deveRetornarStatus200_QuandoConsultarrestaurants() {
	    	RestAssured.given()
	            .accept(ContentType.JSON)
	        .when()
	            .get()
	        .then()
	            .statusCode(HttpStatus.OK.value());
	    }
	    
	    @Test
	    public void deveRetornarStatus201_QuandoCadastrarrestaurant() {
	    	RestAssured.given()
	            .body(jsonrestaurantCorreto)
	            .contentType(ContentType.JSON)
	            .accept(ContentType.JSON)
	        .when()
	            .post()
	        .then()
	            .statusCode(HttpStatus.CREATED.value());
	    }
	    
	    @Test
	    public void deveRetornarStatus400_QuandoCadastrarrestaurantSemrateShipping() {
	    	RestAssured.given()
	            .body(jsonrestaurantSemFrete)
	            .contentType(ContentType.JSON)
	            .accept(ContentType.JSON)
	        .when()
	            .post()
	        .then()
	            .statusCode(HttpStatus.BAD_REQUEST.value())
	            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	    }

	    @Test
	    public void deveRetornarStatus400_QuandoCadastrarrestaurantSemKitchen() {
	    	RestAssured.given()
	            .body(jsonrestaurantSemKitchen)
	            .contentType(ContentType.JSON)
	            .accept(ContentType.JSON)
	        .when()
	            .post()
	        .then()
	            .statusCode(HttpStatus.BAD_REQUEST.value())
	            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	    }
	    
	    @Test
	    public void deveRetornarStatus400_QuandoCadastrarrestaurantComKitchenInexistente() {
	    	RestAssured.given()
	            .body(jsonrestaurantComKitchenInexistente)
	            .contentType(ContentType.JSON)
	            .accept(ContentType.JSON)
	        .when()
	            .post()
	        .then()
	            .statusCode(HttpStatus.BAD_REQUEST.value())
	            .body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
	    }
	    
	    @Test
	    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarrestaurantExistente() {
	    	RestAssured.given()
	            .pathParam("restaurantId", burgerToprestaurant.getId())
	            .accept(ContentType.JSON)
	        .when()
	            .get("/{restaurantId}")
	        .then()
	            .statusCode(HttpStatus.OK.value())
	            .body("name", equalTo(burgerToprestaurant.getName()));
	    }
	    
	    @Test
	    public void deveRetornarStatus404_QuandoConsultarrestaurantInexistente() {
	    	RestAssured.given()
	            .pathParam("restaurantId", restaurant_ID_INEXISTENTE)
	            .accept(ContentType.JSON)
	        .when()
	            .get("/{restaurantId}")
	        .then()
	            .statusCode(HttpStatus.NOT_FOUND.value());
	    }
	    
	    private void prepararDados() {
	        Kitchen kitchenBrasileira = new Kitchen();
	        kitchenBrasileira.setName("Brasileira");
	        kitchenRepository.save(kitchenBrasileira);

	        Kitchen kitchenAmericana = new Kitchen();
	        kitchenAmericana.setName("Americana");
	        kitchenRepository.save(kitchenAmericana);
	        
	        burgerToprestaurant = new Restaurant();
	        burgerToprestaurant.setName("Burger Top");
	        burgerToprestaurant.setRateShipping(new BigDecimal(10));
	        burgerToprestaurant.setKitchen(kitchenAmericana);
	        restaurantRepository.save(burgerToprestaurant);
	        
	        var comidaMineirarestaurant = new Restaurant();
	        comidaMineirarestaurant.setName("Comida Mineira");
	        comidaMineirarestaurant.setRateShipping(new BigDecimal(10));
	        comidaMineirarestaurant.setKitchen(kitchenBrasileira);
	        restaurantRepository.save(comidaMineirarestaurant);
	    }            
}
