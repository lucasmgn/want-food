package com.wantfood.aplication.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable //classe inbodyrável
public class Address {
	
	@Column(name = "address_cep")
	private String cep;
	
	@Column(name = "address_logradouro")
	private String logradouro;
	
	@Column(name = "address_number")
	private String number;
	
	@Column(name = "address_complement")
	private String complement;
	
	@Column(name = "address_neighborhood")
	private String neighborhood;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_city_id")
	private City city;
}
