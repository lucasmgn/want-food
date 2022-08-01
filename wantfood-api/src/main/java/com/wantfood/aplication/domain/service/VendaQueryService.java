package com.wantfood.aplication.domain.service;

import java.util.List;

import com.wantfood.aplication.domain.filter.VendaDiariaFilter;
import com.wantfood.aplication.domain.model.dto.VendaDiaria;

public interface VendaQueryService {
	
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffset);
} 
