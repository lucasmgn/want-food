package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.KitchenDTOAssembler;
import com.wantfood.aplication.api.assembler.KitchenInputDisassembler;
import com.wantfood.aplication.api.model.KitchenDTO;
import com.wantfood.aplication.api.model.input.KitchenInputDTO;
import com.wantfood.aplication.api.openapi.controller.KitchenControllerOpenApi;
import com.wantfood.aplication.domain.model.Kitchen;
import com.wantfood.aplication.domain.repository.KitchenRepository;
import com.wantfood.aplication.domain.service.KitchenRegistrationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Kitchens")
@RestController
@RequestMapping(value = "/kitchens")
@RequiredArgsConstructor
public class KitchenController implements KitchenControllerOpenApi{

	private final KitchenRepository kitchenRepository;

	private final KitchenRegistrationService registerKitchen;
	
	private final KitchenDTOAssembler kitchenDTOAssembler;
	
	private final KitchenInputDisassembler kitchenInputDisassembler;
	
	/*
	 * Mapeando o metodo list para quando fizerem uma requisição get
	 * Colocando os valores em json
	 * 
	 * Agora vou colocar o findAll para receber premetros pageable para fazer paginação nas listagem
	 * de kitchen
	 * Implmentando a interface Paegeble no metodo list
	 * Mudando de List para page
	 * 
	 * toCollectionModel recebe uma list, então transformei com o .getContent() para extrair os 
	 * elementos da Page
	 * 
	 * Por padrão a amount de elemetos por pagina é 20
	 * Modificando o size das pages, no Params do Postman com:
	 * key = size e no value [amount desejada]
	 * 
	 * Modificando com anotação e deixando o valor default, @PageableDefault(size = [sizeDesejado])
	 *  
	 * Mudando as paginas: no Params do Postman utilizando - key = page e
	 *  value = [qual página vc deseja visualizar] obs: começa da pagina 0
	 *  
	 *  Para ordenar os resultados da lista, existe a propriedade sort,
	 *  key = sort e value = [name da propriedade que será ordenada], por ex: id e name
	 *  para ordem descrecente, utiliza o ",desc" depois do name da propriedade, ex: id,desc
	 * */
	@GetMapping
	public Page<KitchenDTO> list(@PageableDefault(size = 10) Pageable pageable) {
		
		var kitchensPages = kitchenRepository.findAll(pageable);
		var kitchenDTO = kitchenDTOAssembler.toCollectionModel(kitchensPages.getContent());
		
		//Instanciando um new PageImpl, passando a lista kitchenDTO,
		//pegeable e o total de elementos da page
		return new PageImpl<>(kitchenDTO, pageable,
				kitchensPages.getTotalElements());
	}

	@GetMapping(value = "/{kitchenId}")
	public KitchenDTO find(@PathVariable Long kitchenId) {
		var kitchen = registerKitchen.fetchOrFail(kitchenId);
		
		return kitchenDTOAssembler.toModel(kitchen);
	}
	
	// Colocando o status como create (status 201), e irá retornar a kitchen criada 
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public KitchenDTO add(@RequestBody @Valid KitchenInputDTO kitchenInputDTO) {
		
		var kitchen = kitchenInputDisassembler.toDomainObject(kitchenInputDTO);
		kitchen = registerKitchen.add(kitchen);
		
		return kitchenDTOAssembler.toModel(kitchen);
	}

	@PutMapping("/{kitchenId}")
	public KitchenDTO update(@PathVariable Long kitchenId,
								@RequestBody @Valid KitchenInputDTO kitchenInputDTO) {
		
		var kitchenAtual = registerKitchen.fetchOrFail(kitchenId);
		kitchenInputDisassembler.copyToDomainObject(kitchenInputDTO, kitchenAtual);
		kitchenAtual = registerKitchen.add(kitchenAtual);
		
		return kitchenDTOAssembler.toModel(kitchenAtual);
	}
	
	@DeleteMapping("/{kitchenId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long kitchenId){
		registerKitchen.delete(kitchenId);
	}

}
