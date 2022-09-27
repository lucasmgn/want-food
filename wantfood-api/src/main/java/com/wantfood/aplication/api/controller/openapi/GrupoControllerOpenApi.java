package com.wantfood.aplication.api.controller.openapi;

import java.util.List;

import com.wantfood.aplication.api.exceptionhandler.Problem;
import com.wantfood.aplication.api.model.GrupoDTO;
import com.wantfood.aplication.api.model.input.GrupoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {
	
	@ApiOperation("Lista os grupos")
	public List<GrupoDTO> listar();
	
	@ApiOperation("Busca grupo por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do grupo inválido",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado",
		content = @Content(mediaType = "application/json",
		schema = @Schema(implementation = Problem.class)))
	})
	public GrupoDTO buscar(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);
	
	@ApiOperation("Cadastra um grupo")
	@ApiResponses({	
		@ApiResponse(responseCode = "201", description = "Grupo Cadastrado")
	})
	public GrupoDTO adicionar(GrupoInputDTO grupoInputDTO);
	
	@ApiOperation("Atualiza grupo por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "200", description = "Grupo Atualizado"),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado",
		content = @Content(mediaType = "application/json",
		schema = @Schema(implementation = Problem.class)))
	})
	public GrupoDTO atualizar(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId,
			GrupoInputDTO grupoInputDTO);
	
	@ApiOperation("Exclui um grupo por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "204", description = "Grupo excluído",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado",
		content = @Content(mediaType = "application/json",
		schema = @Schema(implementation = Problem.class)))
	})
	public void remover(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);
}
