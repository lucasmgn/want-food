package com.wantfood.aplication.infrastructure.repository.spec;

import com.wantfood.aplication.domain.filter.OrderFilter;
import com.wantfood.aplication.domain.model.Order;
import com.wantfood.aplication.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

//Especification
public class OrderSpecs {

	public static Specification<Order> usingFilter(OrderFilter filter){
		return (root, query, criteriaBuilder) ->{
			
			/*
			 * Verificando que se o ResultType dele for do tipo order, então ele fará os fetch,
			 * evitando o org.hibernate.QueryException, ja que estou fazend paginação
			 *  no metodo research de order
			 * */
			if(Order.class.equals(query.getResultType())) {
				//Resolvendo problemas de multiplos selects (N+1)
				root.fetch("restaurant").fetch("kitchen");
				root.fetch("client");
			}

			var predicates = new ArrayList<Predicate>();
			
			if(filter.getClientId() != null) {
				predicates.add(criteriaBuilder.equal(root.get("client"), filter.getClientId()));
			}
			if(filter.getRestaurantId() != null) {
				predicates.add(criteriaBuilder.equal(root.get("restaurant"), filter.getRestaurantId()));
			}
			
			//Datas maiores ou igual a selecionada
			if(filter.getCreationDateStart() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"),
						filter.getCreationDateStart()));
			}
			
			//Datas maiores ou igual a selecionada
			if(filter.getCreationDateEnd() != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"),
						filter.getCreationDateEnd()));
			}
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};

	}
	
	public static Specification<Restaurant> comnameSemelhante(String name){
		return (root, query, criteriaBuilder) -> 
			criteriaBuilder.like(root.get("name"), "%" + name + "%");
	}
}

