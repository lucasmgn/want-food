package com.wantfood.aplication.infrastructure.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.wantfood.aplication.domain.filter.VendaDiariaFilter;
import com.wantfood.aplication.domain.model.Pedido;
import com.wantfood.aplication.domain.model.StatusPedido;
import com.wantfood.aplication.domain.model.dto.VendaDiaria;
import com.wantfood.aplication.domain.service.VendaQueryService;

@Repository
public class VendaQueryImpl implements VendaQueryService{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, 
			String timeOffset) {
		
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class); // tipo de consulta que ela retorna
		var root = query.from(Pedido.class);	
		var predicates = new ArrayList<Predicate>();
		
		//Convertendo data
		var fuctionConvertTzDataCriacao = builder.function("convert_tz", Date.class,
				root.get("dataCriacao"), builder.literal("+00:00"), builder.literal(timeOffset));
		
		var functionDataCriacao = builder.function("date", Date.class, fuctionConvertTzDataCriacao);
		
		var selection = builder.construct(VendaDiaria.class, 
				functionDataCriacao,
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
	
		if(filter.getRestauranteId() != null) {
			predicates.add(
					builder.equal(root.get("restaurante"), filter.getRestauranteId()));
		}
		
		if(filter.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
					filter.getDataCriacaoInicio()));
		}
		
		if(filter.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
					filter.getDataCriacaoFim()));
		}
		
		predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		query.select(selection);
		query.groupBy(functionDataCriacao);
		query.where(predicates.toArray(new Predicate[0]));
		
		var retorno = manager.createQuery(query).getResultList();
		
		return retorno;
	}
}
