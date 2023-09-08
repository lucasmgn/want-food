package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.filter.SaleDailyFilter;

public interface SellReportService {
	
	byte[] issueDailySales(SaleDailyFilter filter, String timeOffset);
}
