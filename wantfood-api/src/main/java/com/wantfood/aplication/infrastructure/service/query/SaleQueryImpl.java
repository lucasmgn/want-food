package com.wantfood.aplication.infrastructure.service.query;

import com.wantfood.aplication.domain.filter.SaleDailyFilter;
import com.wantfood.aplication.domain.model.Order;
import com.wantfood.aplication.domain.model.OrderStatus;
import com.wantfood.aplication.domain.model.dto.SaleDaily;
import com.wantfood.aplication.domain.service.SaleQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SaleQueryImpl implements SaleQueryService{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<SaleDaily> consultDailySales(SaleDailyFilter filter, 
			String timeOffset) {
		
		//Utilizando criteria builder para find os dados no bd
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(SaleDaily.class); // tipo de consulta que ela retorna
		var root = query.from(Order.class);
		var predicates = new ArrayList<Predicate>();
		
		//Convertendo data
		var fuctionConvertTzcreationDate = builder.function("convert_tz", Date.class,
				root.get("creationDate"), builder.literal("+00:00"), builder.literal(timeOffset));
		
		var functioncreationDate = builder.function("date", Date.class, fuctionConvertTzcreationDate);
		
		var selection = builder.construct(SaleDaily.class, 
				functioncreationDate,
				builder.count(root.get("id")),
				builder.sum(root.get("amount")));
	
		if(filter.getRestaurantId() != null) {
			predicates.add(
					builder.equal(root.get("restaurant"), filter.getRestaurantId()));
		}
		
		if(filter.getCreationDateStart() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"),
					filter.getCreationDateStart()));
		}
		
		if(filter.getCreationDateEnd() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"),
					filter.getCreationDateEnd()));
		}
		
		predicates.add(root.get("status").in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED));
		
		query.select(selection);
		query.groupBy(functioncreationDate);
		query.where(predicates.toArray(new Predicate[0]));

		return manager.createQuery(query).getResultList();
	}
}
