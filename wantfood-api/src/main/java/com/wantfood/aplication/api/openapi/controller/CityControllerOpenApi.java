package com.wantfood.aplication.api.openapi.controller;

import java.util.List;

import com.wantfood.aplication.api.exceptionhandler.Problem;
import com.wantfood.aplication.api.model.CityDTO;
import com.wantfood.aplication.api.model.input.CityInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/*
 * Interface criada para desaclopar as anotações do swagger no controlador de city
 * 
 * @Api(tags = "Citys") anotação para marcar o controlador como recuso do swagger
 * @ApiOperation("Lista as citys") Mudando o name do metodo na page do Swagger
 * @ApiParam(value = "ID de uma city") alterando a descrição no swagger
 * @ApiResponses, anotação do swagger que recebe um array de @ApiResponse
 *  
 * @ApiResponse(responseCode = "200", description = "City excluída", anotação pega o código selecionado
 *  e muda sua descrição, caso o código não esteja presente na documentação, ele será adicionado	
 * 
 * content = @Content(mediaType = "application/json", mostra mídia que é aceita
 * 			
 * schema = @Schema(implementation = Problem.class))), implementação da classe problema 
 * */
@Api(tags = "Citys")
public interface CityControllerOpenApi {
	
	@ApiOperation("Lista as citys")
	List<CityDTO> list();
	
	
	@ApiOperation("Busca uma city por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "400", description = "ID da city inválido",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "City não encontrada",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class)))
	})
	public CityDTO find(@ApiParam(value = "ID de uma city") Long cityId);
	
	
	@ApiOperation("Cadastra uma city")
	@ApiResponses({	
		@ApiResponse(responseCode = "201", description = "City Cadastrada")
	})
	public CityDTO add(@ApiParam(name = "body", value = "Representação de uma nova city")
			CityInputDTO cityInputDTO);
	
	@ApiOperation("Atualiza uma city por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "200", description = "City Atualizada"),
		@ApiResponse(responseCode = "404", description = "City não encontrada",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class)))
	})
	public CityDTO atualizar(@ApiParam(value = "ID de uma city") Long cityId,
                             CityInputDTO cityInputDTO);
	
	@ApiOperation("Exclui uma city por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "204", description = "City excluída",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "City não encontrada",
		content = @Content(mediaType = "application/json",
		schema = @Schema(implementation = Problem.class)))
	})
	public void remove(@ApiParam(value = "ID de uma city") Long cityId);
}
