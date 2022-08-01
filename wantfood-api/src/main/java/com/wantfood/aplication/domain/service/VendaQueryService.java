package com.wantfood.aplication.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.filter.VendaDiariaFilter;
import com.wantfood.aplication.domain.model.dto.VendaDiaria;

@Service
public interface VendaQueryService {
	
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffset);
} 
