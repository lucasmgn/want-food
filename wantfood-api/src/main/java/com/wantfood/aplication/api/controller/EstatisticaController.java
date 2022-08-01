package com.wantfood.aplication.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wantfood.aplication.domain.filter.VendaDiariaFilter;
import com.wantfood.aplication.domain.model.dto.VendaDiaria;
import com.wantfood.aplication.domain.service.VendaQueryService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {
	
	@Autowired
	private VendaQueryService service;
	
	//Se o consumidor não especificar nada no param, utilizará o UTC
	@GetMapping("/vendas-diarias")
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		return service.consultarVendasDiarias(filter, timeOffset);
	}
}
