package com.wantfood.aplication.infrastructure.repository;

import static com.wantfood.aplication.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.wantfood.aplication.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.wantfood.aplication.domain.model.Restaurante;
import com.wantfood.aplication.domain.repository.RestauranteRepository;
import com.wantfood.aplication.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
	//Utilizando JPQL
//	@Override
//	public List<Restaurante> find(String nome, BigDecimal freteInicial, BigDecimal freteFinal){
//		
//		var jpql = new StringBuilder();
//		jpql.append("from Restaurante where 0 = 0 ");
//		
//		var parametros = new HashMap<String, Object>();
//		
//		if(StringUtils.hasLength(nome)) {
//			jpql.append("and nome like :nome ");
//			parametros.put("nome", "%" + nome + "%");
//		}
//		
//		if(freteInicial != null) {
//			jpql.append("and taxaFrete >= :taxaInicial ");
//			parametros.put("taxaInicial", freteInicial);
//		}
//		
//		if(freteFinal != null) {
//			jpql.append("and taxaFrete <= :taxaFinal ");
//			parametros.put("taxaFinal", freteFinal);
//		}
//		
//		TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);
//		
//		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
//		
//		return query.getResultList();
//	}
	
	//Utilizando Criteria
	@Override
	public List<Restaurante> find(String nome, BigDecimal freteInicial, BigDecimal freteFinal){
		
		/*
		 * Usado para construir consultas de critérios, seleções compostas, expressões,
		 * predicados, ordenações.
		 * 
		 * Observe que Predicate é usado em vez de Expression<Boolean> nesta API para contornar o fato
		 *  de que Javagenerics não é compatível com varags.
		 * */
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		//Responsável por implementar uma estrutura de uma query
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class);
		
		/*
		 * Predicado é um filtro, O tipo de um predicado simples ou composto:
		 *  uma conjunção ou disjunção de restrições.
		 * Um predicado simples é considerado uma conjunção com um único conjunto.
		 * */ 
		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		
		if(freteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), freteInicial));
		}
	
		if(freteFinal != null) {
			predicates.add( builder.lessThanOrEqualTo(root.get("taxaFrete"), freteFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		
		return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
	}
	

}
