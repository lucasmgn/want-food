package com.wantfood.aplication.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wantfood.aplication.api.exceptionhandler.Problem;

import springfox.documentation.service.Response;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * @EnableSwagger2 Vai habilitar o suporte ao swagger 2
 * 
 * @Import(BeanValidatorPluginsConfiguration.class) adicionando a anotação para utilização
 * das anotações do bean validation
 * 
 * @EnableWebMvc para o suporte a serialização de objetos para JSON, XML, etc
 * */
@EnableWebMvc
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

	//fazer com que o SpringFox carregue o módulo de conversão de datas
    @Bean
    JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }
	
	/*
	 * Page do Swagger
	 * .apis(RequestHandlerSelectors.basePackage("com.wantfood.aplication.api")), escaneando os
	 * controladores apenas desse pacote selecionado
	 * 
	 * .tags, configurando as tags, adicionando no controlador de cidade a anotação @Api(tags = "Cidades"),
	 * para fazer a referencia
	 * 
	 * .useDefaultResponseMessages(false), desabilitando os cod de error padrão e implementando o meu.
	 * 
	 * .globalResponses, descreve os codigos de retorno padrão para metodo escolhido(GET)
	 * 
	 * additionalModels(tupeResolver.resolve(Problem.class)), adicionando um novo modelo 
	 * para o springfox escanear, já que ele escaneia apenas controladores e o Problem não
	 *  está em nenhuma classe de controlador
	 * */
	@Bean
	Docket apiDocket() {
		
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.wantfood.aplication.api")) 
					.paths(PathSelectors.any())
					.build()
					.useDefaultResponseMessages(false)
					.globalResponses(HttpMethod.GET, globalGetResponseMessages())
					.globalResponses(HttpMethod.POST, globalPutPostResponseMessages())
					.globalResponses(HttpMethod.PUT, globalPutPostResponseMessages())
					.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
					.additionalModels(typeResolver.resolve(Problem.class))
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Gerencia as cidades"))
				.tags(new Tag("Cozinhas", "Gerencia as cozinhas"))
				.tags(new Tag("Grupos", "Gerencia grupos de usuários"));
	}
	
	//Implementando os codigos de error de todo os métodos GET da aplicação
	private List<Response> globalGetResponseMessages(){
		return Arrays.asList(
				internalServerError(),
				notAcceptable()
		);
	}
	
	//Implementando os codigos de error de todo os métodos Delete da aplicação
	private List<Response> globalDeleteResponseMessages(){
		return Arrays.asList(
				badRequest(),
				internalServerError()
		);
	}
	
	//Implementando os codigos de error de todo os métodos PUT e POST da aplicação
	private List<Response> globalPutPostResponseMessages(){
		return Arrays.asList(
				badRequest(),
				internalServerError(),
				notAcceptable(),
				unsupportedMediaType()
		);
	}
	
	//Metodo para representar uma resposta de BadRequest
	private Response badRequest() {
		return new ResponseBuilder()
		.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		.description("Requisição inválida (erro do cliente)")
		.representation(MediaType.APPLICATION_JSON)
		.apply(getProblemReferencia())
		.build();
	}

	//Metodo para representar uma resposta de notAcceptable
	private Response notAcceptable() {
		return new ResponseBuilder()
		.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		.description("Recurso não possui representação que pode ser aceita pelo consumidor")
		.build();
	}
	
	//Metodo para representar uma resposta de internalServerError
	private Response internalServerError() {
		return new ResponseBuilder()
		.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		.description("Erro interno do servidor")
		.representation(MediaType.APPLICATION_JSON)
		.apply(getProblemReferencia())
		.build();
	}
	
	//Metodo para representar uma resposta de UNSUPPORTED_MEDIA_TYPE
	private Response unsupportedMediaType() {
		return new ResponseBuilder()
		.code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
		.description("Requisição recusada porque o corpo está em um formato não suportado")
		.representation(MediaType.APPLICATION_JSON)
		.apply(getProblemReferencia())
		.build();
	}
	
	//Gerando referência para classe Problem 
	private Consumer<RepresentationBuilder> getProblemReferencia() {
		return r -> r.model(m -> m.name("Problema")
				.referenceModel(ref -> ref.key( k -> k.qualifiedModelName(q -> q.name("Problema")
						.namespace("com.wantfood.aplication.api.exceptionhandler")))));
	}
	
	/*
	 * Modificando page do Swagger UI, alterando titulo, descrição e versão e contato com nome da empresa,
	 * site e e-mail
	 * */
	private ApiInfo apiInfo() {
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
