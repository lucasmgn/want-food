package com.wantfood.aplication.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wantfood.aplication.domain.filter.VendaDiariaFilter;
import com.wantfood.aplication.domain.model.dto.VendaDiaria;
import com.wantfood.aplication.domain.service.VendaQueryService;
import com.wantfood.aplication.domain.service.VendaReportService;

@RestController
@RequestMapping(value = "/estatisticas")
public class EstatisticaController {
	
	@Autowired
	private VendaQueryService service;
	
	@Autowired
	private VendaReportService reportService;
	
	//Se o consumidor não especificar nada no param, utilizará o UTC
	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		return service.consultarVendasDiarias(filter, timeOffset);
	}
	
	//gerando relatorio
	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		
		byte[] pdf = reportService.emitirVendasDiarias(filter, timeOffset);
		
		/*
		 * Criando cabeçalho http, "attachment" significa que o que estou retornando na resposta,
		 *  precisa ser baixado pelo cliente
		 *  filename nome do arquivo de destino
		 * */
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(pdf);
	}
}
