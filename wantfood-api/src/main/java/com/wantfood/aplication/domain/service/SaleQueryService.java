package com.wantfood.aplication.domain.service;

import java.util.List;

import com.wantfood.aplication.domain.filter.SaleDailyFilter;
import com.wantfood.aplication.domain.model.dto.SaleDaily;

public interface SaleQueryService {
	
	List<SaleDaily> consultDailySales(SaleDailyFilter filter, String timeOffset);
} 
