package com.wantfood.aplication.infrastructure.repository.spec;

import com.wantfood.aplication.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecs {
	
	public static Specification<Restaurant> comFreteGratis(){
		return (root, query, criteriaBuilder) ->
			criteriaBuilder.equal(root.get("rateShipping"), BigDecimal.ZERO);
	}
	
	public static Specification<Restaurant> comNameSemelhante(String name){
		return (root, query, criteriaBuilder) -> 
			criteriaBuilder.like(root.get("name"), "%" + name + "%");
	}
}
