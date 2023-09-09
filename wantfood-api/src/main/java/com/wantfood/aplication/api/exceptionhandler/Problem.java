package com.wantfood.aplication.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

/*
 * Padronizando o formato de problemas
 * no body de respostas com a RFC 7807
 * 
 * @ApiModel("Problema"), para namear na documentação como Problema
 * @ApiModelProperty para documentação, adicioando uma exeplo
 * */
@ApiModel("Problema")
@JsonInclude(Include.NON_NULL) //incluindo as propriedades que não são nulas
@Getter
@Builder //Cria um padrão de projeto
public class Problem {

	@ApiModelProperty(example = "400")
	private Integer status;
	
	@ApiModelProperty(example = "https//wantfood.com.br/erro-na-Message")
	private String type;
	
	@ApiModelProperty(example = "Dados inválidos")
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos inválidos. Faça o preenchimento correto e tente novamente.")
	private String detail;
	
	@ApiModelProperty(example = "Um ou mais campos inválidos. Faça o preenchimento correto e tente novamente.")
	private String userMessage;
	
	@ApiModelProperty(example = "2022-09-01T18:09:02.70844Z")
	private OffsetDateTime timestamp;
	
	@ApiModelProperty("Lista de objetos ou campos que geraram o erro(opcional)")
	private List<Object> objects;
	
	/*
	 *Sendo criado uma classe para list as causa dos problemas inicialmente
	 * causados por MethodArgumentNotValidException
	 * */
	@ApiModel("ObjetoProblema")
	@Getter
	@Builder
	public static class Object{
		
		@ApiModelProperty(example = "Preço")
		private String name;
		
		@ApiModelProperty(example = "O preço é obrigatório")
		private String userMessage;
	}

}
