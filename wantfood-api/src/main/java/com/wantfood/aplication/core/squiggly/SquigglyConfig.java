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
	 *Exemplo utilizando o endpoint da entidade pedido
	 *
	 *(key = fields value = codigo) = mostrará apenas a propriedade codigo
	 *(key = fields value = codigo,valorTotal) = mostrará as propriedades codigo e valorTotal
	 *(key = fields value = sub*) = mostrará as propriedade que começam com "sub"
	 *(key = fields value = cliente) = mostrará o objeto cliente com suas propriedades
	 *(key = fields value = cliente.id) = mostra o objeto cliente e apenas a propriedade id
	 *(key = fields value = codigo,valorTotal,sub*,cliente.id) = mostrando todos os atributos
	 *
	 *(key = fields value = cliente[id,nome]) = mostrando o atributo id e nome
	 *(key = fields value = cliente[-id]) = mostrando todos os atributos, menos o id
	 *(key = fields value = -codigo,-restaurante) = mostrando todos os atributos da entidade
	 *menos o codigo e o objeto restaurante
	 *
	 *OBS: lembrar que o Tomcat não aceita os "[]", alternativa para resolver o problema
	 *é utilizando os enconding de "[]" que seriam o %5B para o "[" e %5D para o "]".
	 *Podemos também adicionar os [] com um custumizador do Tomcat, Ele se chama Tomcatcustomizer
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
		 * Iniciando o Squiggly, para mudar a chave de fields para um nome da minha escolha
		 * eu posso adicionar nos parametros new RequestSquigglyContextProvider("campos", null),
		 * mudando o nome de fields para campos
		 * */
		Squiggly.init(objectMapper, new RequestSquigglyContextProvider());
		
		//Tudo o que começar com /pedidos ou /restaurantes, o Squiggly irá filtrar, os demais não
		var urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");
		
		var filterResgitration = new FilterRegistrationBean<SquigglyRequestFilter>();
		filterResgitration.setFilter(new SquigglyRequestFilter());
		
		//Adicionando a restrição, funcionalidade ativa apenas para pedidos e restaurantes
		filterResgitration.setUrlPatterns(urlPatterns);
		
		return filterResgitration;
	}
}
