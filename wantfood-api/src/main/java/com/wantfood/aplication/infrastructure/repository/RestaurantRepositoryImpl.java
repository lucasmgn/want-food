package com.wantfood.aplication.infrastructure.repository;

import com.wantfood.aplication.domain.model.Restaurant;
import com.wantfood.aplication.domain.repository.RestaurantRepository;
import com.wantfood.aplication.domain.repository.RestaurantRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.wantfood.aplication.infrastructure.repository.spec.RestaurantSpecs.comFreteGratis;
import static com.wantfood.aplication.infrastructure.repository.spec.RestaurantSpecs.comNameSemelhante;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired @Lazy
	private RestaurantRepository restaurantRepository;
	
	//Utilizando JPQL
//	@Override
//	public List<restaurant> find(String name, BigDecimal freteInicial, BigDecimal freteFinal){
//		
//		var jpql = new StringBuilder();
//		jpql.append("from restaurant where 0 = 0 ");
//		
//		var parametros = new HashMap<String, Object>();
//		
//		if(StringUtils.hasLength(name)) {
//			jpql.append("and name like :name ");
//			parametros.put("name", "%" + name + "%");
//		}
//		
//		if(freteInicial != null) {
//			jpql.append("and rateShipping >= :taxaInicial ");
//			parametros.put("taxaInicial", freteInicial);
//		}
//		
//		if(freteFinal != null) {
//			jpql.append("and rateShipping <= :taxaFinal ");
//			parametros.put("taxaFinal", freteFinal);
//		}
//		
//		TypedQuery<restaurant> query = entityManager.createQuery(jpql.toString(), restaurant.class);
//		
//		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
//		
//		return query.getResultList();
//	}
	
	//Utilizando Criteria
	@Override
	public List<Restaurant> find(String name, BigDecimal freteInicial, BigDecimal freteFinal){
		
		/*
		 * Usado para construir consultas de critérios, seleções compostas, expressões,
		 * predicados, ordenações.
		 * 
		 * Observe que Predicate é usado em vez de Expression<Boolean> nesta API para contornar o fato
		 *  de que Javagenerics não é compatível com varags.
		 * */
		var builder = entityManager.getCriteriaBuilder();
		
		//Responsável por implementar uma estrutura de uma query
		var criteria = builder.createQuery(Restaurant.class);
		var root = criteria.from(Restaurant.class);
		
		/*
		 * Predicado é um filtro, O tipo de um predicado simples ou composto:
		 *  uma conjunção ou disjunção de restrições.
		 * Um predicado simples é considerado uma conjunção com um único conjunto.
		 * */ 
		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(name)) {
			predicates.add(builder.like(root.get("name"), "%" + name + "%"));
		}
		
		if(freteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("rateShipping"), freteInicial));
		}
	
		if(freteFinal != null) {
			predicates.add( builder.lessThanOrEqualTo(root.get("rateShipping"), freteFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = entityManager.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Restaurant> findComFreteGratis(String name) {
		
		return restaurantRepository.findAll((Sort) comFreteGratis().and(comNameSemelhante(name)));
	}

}
