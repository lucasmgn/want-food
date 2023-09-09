package com.wantfood.aplication.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemOrder {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer amount;
	
	private BigDecimal unitPrice;
	
	private BigDecimal totalPrice;
	
	private String observation;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Order order;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Product product;
	
	public void calcularPrecoTotal() {
	    var unitPrice = this.getUnitPrice();
	    var amount = this.getAmount();

	    if (unitPrice == null) {
	        unitPrice = BigDecimal.ZERO;
	    }

	    if (amount == null) {
			amount = 0;
	    }

	    this.setTotalPrice(unitPrice.multiply(new BigDecimal(amount)));
	}
}
