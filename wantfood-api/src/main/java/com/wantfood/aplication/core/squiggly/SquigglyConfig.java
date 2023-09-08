package com.wantfood.aplication.core.squiggly;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

/*
 * Classe de configuração do Squiggly, meio recioso de usar ela porque é um projeto pequeno
 * e não sei até quando ele irá continuar no ar
 * */
@Configuration
public class SquigglyConfig {
	
	/*
	 *Método que vai produzir uma instancia de FilterRegistrationBean, 
	 *atribuindo o filtro da instancia, adicionando um filtro, sempre que uma requisição
	 *chegar na api, ela passará por esse filtro e esse filtro do Squiggly ele vai ter a chance
	 *de fazer algo
	 *
	 *Ajuda a fazer filtros das requisições
	 *
	 *Exemplo utilizando o endpoint da entidade order
	 *
	 *(key = fields value = code) = mostrará apenas a propriedade code
	 *(key = fields value = code,amount) = mostrará as propriedades code e amount
	 *(key = fields value = sub*) = mostrará as propriedade que começam com "sub"
	 *(key = fields value = client) = mostrará o objeto client com suas propriedades
	 *(key = fields value = client.id) = mostra o objeto client e apenas a propriedade id
	 *(key = fields value = code,amount,sub*,client.id) = mostrando todos os atributos
	 *
	 *(key = fields value = client[id,name]) = mostrando o atributo id e name
	 *(key = fields value = client[-id]) = mostrando todos os atributos, menos o id
	 *(key = fields value = -code,-restaurant) = mostrando todos os atributos da entidade
	 *menos o code e o objeto restaurant
	 *
	 *OBS: lembrar que o Tomcat não aceita os "[]", alternativa para resolver o problema
	 *é utilizando os enconding de "[]" que seriam o %5B para o "[" e %5D para o "]".
	 *Podemos também add os [] com um custumizador do Tomcat, Ele se chama Tomcatcustomizer
	 *o link https://gist.github.com/thiagofa/ce48c08e4caae34c5dca0a7a5c252666
	 *
	 *Se colocar no value um atributo que não existe, ele irá mostrá aqueles que existem
	 *
	 * ObjectMapper, é uma classe do jackson, vai servir para que o squiggly 
	 * consiga customizar(filtrar as propriedades).
	 * 
	 * RequestSquigglyContextProvider(), possuirá um fields que será utilizado como 
	 * Key nos params do Postman
	 * */	
	@Bean
	FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper){
		
		/*
		 * Iniciando o Squiggly, para mudar a chave de fields para um name da minha escolha
		 * eu posso add nos parametros new RequestSquigglyContextProvider("campos", null),
		 * mudando o name de fields para campos
		 * */
		Squiggly.init(objectMapper, new RequestSquigglyContextProvider());
		
		//Tudo o que começar com /orders ou /restaurants, o Squiggly irá filtrar, os demais não
		var urlPatterns = Arrays.asList("/orders/*", "/restaurants/*");
		
		var filterResgitration = new FilterRegistrationBean<SquigglyRequestFilter>();
		filterResgitration.setFilter(new SquigglyRequestFilter());
		
		//Adicionando a restrição, funcionalidade ativa apenas para orders e restaurants
		filterResgitration.setUrlPatterns(urlPatterns);
		
		return filterResgitration;
	}
}
