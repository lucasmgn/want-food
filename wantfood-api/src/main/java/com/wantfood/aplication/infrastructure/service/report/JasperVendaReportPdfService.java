package com.wantfood.aplication.infrastructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wantfood.aplication.domain.filter.VendaDiariaFilter;
import com.wantfood.aplication.domain.service.VendaQueryService;
import com.wantfood.aplication.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperVendaReportPdfService implements VendaReportService{

	@Autowired
	private VendaQueryService service;
	
	/*
	 * Tive problema de Error evaluating expression for source text: DATEFORMAT($F{data},"dd/MM/yyyy" )
	 * consertei indo no Jasper e alterando a tag para:
	 * <textFieldExpression><![CDATA[new SimpleDateFormat("dd/MM/yyyy")
	 * .format($F{data})]]></textFieldExpression>
	 * 
	 * */
	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset) {	
		try {	
			//Buscando um arquivo
			var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

			var parametros = new HashMap<String, Object>();
			parametros.put("REPORT_LOCALE", new Locale("pt","BR"));
		
			//Fonte de dados
			var vendasDiarias = service.consultarVendasDiarias(filter, timeOffset);
			var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

			//Relatório preenchido
			var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);
			
			//Relatorio em pdf
			return JasperExportManager.exportReportToPdf(jasperPrint);
		}catch(Exception e) {
			throw new ReportException("Não foi possível emitir relatórios de vendas diárias", e);
		}		
	}
}
