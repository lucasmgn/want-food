package com.wantfood.aplication.domain.service;

import com.wantfood.aplication.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
	
	byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset);
}
