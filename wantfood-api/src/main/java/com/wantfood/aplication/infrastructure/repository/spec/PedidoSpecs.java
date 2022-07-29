package com.wantfood.aplication.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.wantfood.aplication.domain.model.Pedido;
import com.wantfood.aplication.domain.model.Restaurante;
import com.wantfood.aplication.domain.repository.filter.PedidoFilter;

//Especification
public class PedidoSpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filter){
		return (root, query, criteriaBuilder) ->{
			
			/*
			 * Verificando que se o ResultType dele for do tipo pedido, então ele fará os fetch,
			 * evitando o org.hibernate.QueryException, ja que estou fazend paginação
			 *  no metodo pesquisar de pedido
			 * */
			if(Pedido.class.equals(query.getResultType())) {
				//Resolvendo problemas de multiplos selects (N+1)
				root.fetch("restaurante").fetch("cozinha");
				root.fetch("cliente");
			}

			var predicates = new ArrayList<Predicate>();
			
			if(filter.getClienteId() != null) {
				predicates.add(criteriaBuilder.equal(root.get("cliente"), filter.getClienteId()));
			}
			if(filter.getRestauranteId() != null) {
				predicates.add(criteriaBuilder.equal(root.get("restaurante"), filter.getRestauranteId()));
			}
			
			//Datas maiores ou igual a selecionada
			if(filter.getDataCriacaoInicio() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"),
						filter.getDataCriacaoInicio()));
			}
			
			//Datas maiores ou igual a selecionada
			if(filter.getDataCriacaoFim() != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"),
						filter.getDataCriacaoFim()));
			}
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};

	}
	
	public static Specification<Restaurante> comNomeSemelhante(String nome){
		return (root, query, criteriaBuilder) -> 
			criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
	}
}

