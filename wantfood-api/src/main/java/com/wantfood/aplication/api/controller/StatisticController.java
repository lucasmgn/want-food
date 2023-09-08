package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.domain.filter.SaleDailyFilter;
import com.wantfood.aplication.domain.model.dto.SaleDaily;
import com.wantfood.aplication.domain.service.SaleQueryService;
import com.wantfood.aplication.domain.service.SellReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/estatisticas")
@RequiredArgsConstructor
public class StatisticController {
	
	private final SaleQueryService service;
	
	private final SellReportService reportService;
	
	//Se o consumidor não especificar nada no param, utilizará o UTC
	@GetMapping(value = "/vendas-diarias")
	public List<SaleDaily> consultDailySales(SaleDailyFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		return service.consultDailySales(filter, timeOffset);
	}
	
	//gerando relatorio
	@GetMapping(value = "/vendas-diarias")
	public ResponseEntity<byte[]> consultDailySalesPdf(SaleDailyFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		
		byte[] pdf = reportService.issueDailySales(filter, timeOffset);
		
		/*
		 * Criando cabeçalho http, "attachment" significa que o que estou retornando na resposta,
		 *  precisa ser baixado pelo client
		 *  filename name do file de destino
		 * */
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(pdf);
	}
}
