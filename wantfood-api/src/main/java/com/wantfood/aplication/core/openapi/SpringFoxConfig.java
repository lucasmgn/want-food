package com.wantfood.aplication.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@Configuration
@EnableSwagger2 //Vai habilitar o suporte ao swagger 2
public class SpringFoxConfig {
	
	/*
	 * Page do Swagger
	 * .apis(RequestHandlerSelectors.basePackage("com.wantfood.aplication.api")), escaneando os
	 * controladores apenas desse pacote selecionado
	 * 
	 * .tags, configurando as tags, adicionando no controlador de cidade a anotação @Api(tags = "Cidades"),
	 * para fazer a referencia
	 * */
	@Bean
	Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.wantfood.aplication.api")) 
					.paths(PathSelectors.any())
					.build()
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Gerencia as cidades"));
		
	}
	
	/*
	 * Modificando page do Swagger UI, alterando titulo, descrição e versão e contato com nome da empresa,
	 * site e e-mail
	 * */
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("WantFood API")
				.description("API aberta para Clientes e Restaurantes")
				.version("1")
				.contact(new Contact(
						"Wantfood-Company",
						"https://www.linkedin.com/in/lucas-magno-454aa8204/",
						"lucasmagno695@gmail.com"))
				.build();
	}
}
