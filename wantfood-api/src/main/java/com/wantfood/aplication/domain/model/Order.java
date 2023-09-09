package com.wantfood.aplication.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.wantfood.aplication.domain.event.OrderCanceladoEvent;
import com.wantfood.aplication.domain.event.OrderConfirmedEvent;
import com.wantfood.aplication.domain.exception.BusinessException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Order extends AbstractAggregateRoot<Order>{
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	
	private BigDecimal subtotal;
	private BigDecimal rateShipping;	
	private BigDecimal amount;
	
	@Embedded
    private Address addressDelivery;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.CREATED;
	
	@CreationTimestamp
	private OffsetDateTime creationDate; 
	
	private OffsetDateTime dateConfirmation;
	private OffsetDateTime dateCancellation;
	private OffsetDateTime deliveryDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private FormPayment formPayment;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "user_client_id", nullable = false)
	private User client;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<ItemOrder> items = new ArrayList<>();
	
	public void calcularamount() {
		getItems().forEach(ItemOrder::calcularPrecoTotal);
		
		this.subtotal = getItems().stream()
			.map(ItemOrder::getTotalPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		this.amount = this.subtotal.add(this.rateShipping);
	}

	//Método chamado na classe OrderFlowService
	public void confirm() {
		setStatus(OrderStatus.CONFIRMED);
		setDateConfirmation(OffsetDateTime.now());
		
		registerEvent(new OrderConfirmedEvent(this));
	}
	
	public void deliver() {
		setStatus(OrderStatus.DELIVERED);
		setDeliveryDate(OffsetDateTime.now());
	}
	
	public void cancel() {
		setStatus(OrderStatus.CANCELED);
		setDateCancellation(OffsetDateTime.now());
		
		registerEvent(new OrderCanceladoEvent(this));
	}
	
	private void setStatus (OrderStatus statusNovo) {
		
		if(getStatus().naoPodeAlterarPara(statusNovo)) {
			throw new BusinessException(
					String.format(
							"Status do order %s não pode ser alterado de %s para %s", 
							getCode(), getStatus().getDescription(),
							statusNovo.getDescription()));
		}		
		this.status = statusNovo;
	}
	
	
	//Método de callback
	@PrePersist
	private void gerarcode() {
		setCode(UUID.randomUUID().toString());
	}
}
