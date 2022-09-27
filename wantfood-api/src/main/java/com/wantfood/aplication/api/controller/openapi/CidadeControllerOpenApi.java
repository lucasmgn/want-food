package com.wantfood.aplication.api.controller.openapi;

import java.util.List;

import com.wantfood.aplication.api.exceptionhandler.Problem;
import com.wantfood.aplication.api.model.CidadeDTO;
import com.wantfood.aplication.api.model.input.CidadeInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/*
 * Interface criada para desiclopar as anotações do swagger no controlador de cidade
 * 
 * @Api(tags = "Cidades") anotação para marcar o controlador como recuso do swagger
 * @ApiOperation("Lista as cidades") Mudando o nome do metodo na page do Swagger
 * @ApiParam(value = "ID de uma cidade") alterando a descrição no swagger
 * @ApiResponses, anotação do swagger que recebe um array de @ApiResponse
 *  
 * @ApiResponse(responseCode = "200", description = "Cidade excluída", anotação pega o código selecionado
 *  e muda sua descrição, caso o código não esteja presente na documentação, ele será adicionado	
 * 
 * content = @Content(mediaType = "application/json", mostra mídia que é aceita
 * 			
 * schema = @Schema(implementation = Problem.class))), implementação da classe problema 
 * */
@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {
	
	@ApiOperation("Lista as cidades")
	public List<CidadeDTO> listar();
	
	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "400", description = "ID da cidade inválido",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada",
		content = @Content(mediaType = "application/json",
		schema = @Schema(implementation = Problem.class)))
	})
	public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade") Long cidadeId);
	
	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({	
		@ApiResponse(responseCode = "201", description = "Cidade Cadastrada")
	})
	public CidadeDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") 
			CidadeInputDTO cidadeInputDTO);
	
	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "200", description = "Cidade Atualizada"),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada",
		content = @Content(mediaType = "application/json",
		schema = @Schema(implementation = Problem.class)))
	})
	public CidadeDTO atualizar(@ApiParam(value = "ID de uma cidade") Long cidadeId,
			CidadeInputDTO cidadeInputDTO);
	
	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "204", description = "Cidade excluída",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada",
		content = @Content(mediaType = "application/json",
		schema = @Schema(implementation = Problem.class)))
	})
	public void remover(@ApiParam(value = "ID de uma cidade") Long cidadeId);
}
