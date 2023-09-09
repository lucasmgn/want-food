package com.wantfood.aplication.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaleDailyFilter {
	
	private Long restaurantId;  
	
	//para n dar exception, problemas em transformar a string em um offsetDateTime
	@DateTimeFormat(iso = ISO.DATE_TIME) 
	private OffsetDateTime creationDateStart;  
	
	@DateTimeFormat(iso = ISO.DATE_TIME) 
	private OffsetDateTime creationDateEnd; 
}
