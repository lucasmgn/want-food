package com.wantfood.aplication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * Classe responsável por receber requisições web, @Controller
 * 
 * @GetMapping, caminho para chegar no método hello
 * 
 * @ResponseBody retorno será uma resposta da requisição
 * */
@Controller
public class MeuPrimeiroController {
	
	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Olá mundo!";
	}
}
