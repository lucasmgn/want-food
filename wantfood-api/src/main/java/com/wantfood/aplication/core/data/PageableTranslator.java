package com.wantfood.aplication.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

//Uma classe para traduzir as Pageable
public class PageableTranslator {
	
	public static Pageable translate(Pageable pageable,Map<String, String> fieldsMapping) {
		
		//Instanciando um new order 
		var orders = pageable.getSort().stream()
				.filter(order -> fieldsMapping.containsKey(order.getProperty()))
				.map(order -> new Sort.Order(order.getDirection(),
						fieldsMapping.get(order.getProperty())))
				.collect(Collectors.toList());

		//Instanciando uma new Pageable e passand uma lista de orders
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
	}
}
