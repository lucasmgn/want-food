package com.wantfood.aplication.api.openapi.controller;

import java.util.List;

import com.wantfood.aplication.api.exceptionhandler.Problem;
import com.wantfood.aplication.api.model.GroupDTO;
import com.wantfood.aplication.api.model.input.GroupInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Groups")
public interface GroupControllerOpenApi {
	
	@ApiOperation("Lista os groups")
	List<GroupDTO> list();
	
	@ApiOperation("Busca group por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do group inválido",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Group não encontrado",
		content = @Content(mediaType = "application/json",
		schema = @Schema(implementation = Problem.class)))
	})
	public GroupDTO find(@ApiParam(value = "ID de um group", example = "1") Long groupId);
	
	@ApiOperation("Cadastra um group")
	@ApiResponses({	
		@ApiResponse(responseCode = "201", description = "Group Cadastrado")
	})
	public GroupDTO add(GroupInputDTO groupInputDTO);
	
	@ApiOperation("Atualiza group por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "200", description = "Group Atualizado"),
		@ApiResponse(responseCode = "404", description = "Group não encontrado",
		content = @Content(mediaType = "application/json",
		schema = @Schema(implementation = Problem.class)))
	})
	public GroupDTO atualizar(@ApiParam(value = "ID de um group", example = "1") Long groupId,
			GroupInputDTO groupInputDTO);
	
	@ApiOperation("Exclui um group por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "204", description = "Group excluído",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Group não encontrado",
		content = @Content(mediaType = "application/json",
		schema = @Schema(implementation = Problem.class)))
	})
	public void remove(@ApiParam(value = "ID de um group", example = "1") Long groupId);
}
