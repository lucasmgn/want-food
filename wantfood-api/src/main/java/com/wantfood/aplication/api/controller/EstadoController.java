package com.wantfood.aplication.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wantfood.aplication.domain.model.Estado;
import com.wantfood.aplication.domain.repository.EstadoRepository;

@RestController //Possui o @ResponsyBody e o @Controller
@RequestMapping("/estados") //
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping //Mapeando o metodo listar para quando fizerem uma requisição get
	public List<Estado> listar(){
		return estadoRepository.todos();
	}
}
