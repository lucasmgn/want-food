package com.wantfood.aplication.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressDTO {
	
	private String cep;
	private String logradouro;
	private String number;
	private String complement;
	private String neighborhood;
	private CitySummaryDTO city;
}
