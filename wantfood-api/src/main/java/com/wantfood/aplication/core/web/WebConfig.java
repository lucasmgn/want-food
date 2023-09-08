package com.wantfood.aplication.core.web;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
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

    //Irá add o Etag no Response Headers do navegador
    @Bean
    Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
	
}
