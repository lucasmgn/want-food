package com.wantfood.aplication.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.wantfood.aplication.api.exceptionhandler.Problem;
import com.wantfood.aplication.api.model.KitchenDTO;
import com.wantfood.aplication.api.model.input.KitchenInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Kitchens")
public interface KitchenControllerOpenApi {
	
	@ApiOperation("Lista as kitchens")
	public Page<KitchenDTO> list(@PageableDefault(size = 10) Pageable pageable);

	
	@ApiOperation("Busca uma kitchen por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da kitchen inválido",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Kitchen não encontrada",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class)))
	})
	public KitchenDTO find(@ApiParam(value = "ID de uma kitchen", example = "1") Long kitchenId);
	
	
	@ApiOperation("Cadastra uma kitchen")
	@ApiResponses({	
		@ApiResponse(responseCode = "201", description = "Kitchen Cadastrada")
	})
	public KitchenDTO add(@ApiParam(name = "body", value = "Representação de uma nova kitchen")
			KitchenInputDTO kitchenInputDTO);

	
	@ApiOperation("Atualiza uma kitchen por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "200", description = "Kitchen Atualizada"),
		@ApiResponse(responseCode = "404", description = "Kitchen não encontrada",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class)))
	})
	public KitchenDTO atualizar(@ApiParam(value = "Atualiza uma kitchen", example = "1") Long kitchenId,
                                @ApiParam(name = "body", value = "Representação de uma kitchen com news dados") KitchenInputDTO kitchenInputDTO);
	
	
	@ApiOperation("Exclui uma kitchen por ID")
	@ApiResponses({	
		@ApiResponse(responseCode = "204", description = "Kitchen Excluída"),
		@ApiResponse(responseCode = "404", description = "Kitchen não encontrada",
				content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Problem.class)))
	})
	public void remove(@ApiParam(value = "ID de uma kitchen", example = "1") Long kitchenId);

}
