package com.wantfood.aplication.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.wantfood.aplication.api.exceptionhandler.Problem;
import com.wantfood.aplication.api.model.CozinhaDTO;
import com.wantfood.aplication.api.model.input.CozinhaInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {
	
	@ApiOperation("Lista as cozinhas")
	public Page<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable);

	
	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da cozinha inválido",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class)))
	})
	public CozinhaDTO buscar(@ApiParam(value = "ID de uma cozinha", example = "1") Long cozinhaId);
	
	
	@ApiOperation("Cadastra uma cozinha")
	@ApiResponses({	
		@ApiResponse(responseCode = "201", description = "Cozinha Cadastrada")
	})
	public CozinhaDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha")
			CozinhaInputDTO cozinhaInputDTO);

	
	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "200", description = "Cozinha Atualizada"),
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class)))
	})
	public CozinhaDTO atualizar(@ApiParam(value = "Atualiza uma cozinha", example = "1") Long cozinhaId,
			@ApiParam(name = "corpo", value = "Representação de uma cozinha com novos dados") CozinhaInputDTO cozinhaInputDTO);
	
	
	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "204", description = "Cozinha Excluída"),
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class)))
	})
	public void remover(@ApiParam(value = "ID de uma cozinha", example = "1") Long cozinhaId);

}
