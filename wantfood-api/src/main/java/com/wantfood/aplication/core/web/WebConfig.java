package com.wantfood.aplication.core.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Implementando interface para customizar metodos de callback
@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	//Habilitando o cors globalmente
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedMethods("*"); //Todos os métodos 
//		.allowedOrigins("*")
//		.maxAge(5);
	}
	
}
