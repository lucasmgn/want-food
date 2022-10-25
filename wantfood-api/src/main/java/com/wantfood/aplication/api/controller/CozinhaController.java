package com.wantfood.aplication.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wantfood.aplication.api.assembler.CozinhaDTOAssembler;
import com.wantfood.aplication.api.assembler.CozinhaInputDisassembler;
import com.wantfood.aplication.api.model.CozinhaDTO;
import com.wantfood.aplication.api.model.input.CozinhaInputDTO;
import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.repository.CozinhaRepository;
import com.wantfood.aplication.domain.service.CadastroCozinhaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Cozinhas")
//Possui o @ResponsyBody e o @Controller
@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	/*
	 * Mapeando o metodo listar para quando fizerem uma requisição get
	 * Colocando os valores em json
	 * 
	 * Agora vou colocar o findAll para receber premetros pageable para fazer paginação nas listagem
	 * de cozinha
	 * Implmentando a interface Paegeble no metodo listar
	 * Mudando de List para page
	 * 
	 * toCollectionModel recebe uma list, então transformei com o .getContent() para extrair os 
	 * elementos da Page
	 * 
	 * Por padrão a quantidade de elemetos por pagina é 20
	 * Modificando o tamanho das pages, no Params do Postman com:
	 * key = size e no value [quantidade desejada]
	 * 
	 * Modificando com anotação e deixando o valor default, @PageableDefault(size = [tamanhoDesejado])
	 *  
	 * Mudando as paginas: no Params do Postman utilizando - key = page e
	 *  value = [qual página vc deseja visualizar] obs: começa da pagina 0
	 *  
	 *  Para ordenar os resultados da lista, existe a propriedade sort,
	 *  key = sort e value = [nome da propriedade que será ordenada], por ex: id e nome
	 *  para ordem descrecente, utiliza o ",desc" depois do nome da propriedade, ex: id,desc
	 * */
	@ApiOperation("Lista as cozinhas")
	@GetMapping
	public Page<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
		
		Page<Cozinha> cozinhasPages = cozinhaRepository.findAll(pageable);
		
		List<CozinhaDTO> cozinhaDTO = cozinhaDTOAssembler.toCollectionModel
				(cozinhasPages.getContent());
		
		//Instanciando um new PageImpl, passando a lista cozinhaDTO,
		//pegeable e o total de elementos da page
		Page<CozinhaDTO> cozinhaDTOPage = new PageImpl<>(cozinhaDTO, pageable,
				cozinhasPages.getTotalElements());
		
		return cozinhaDTOPage;
	}

	@ApiOperation("Busca uma cozinha por ID")
	@GetMapping(value = "/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cadastroCozinha.buscaOuFalha(cozinhaId);
		
		return cozinhaDTOAssembler.toModel(cozinha);
	}
	
	@ApiOperation("Cadastra uma cozinha")
	// Colocando o status como create (status 201), e irá retornar a cozinha criada 
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInputDTO);
		cozinha = cadastroCozinha.adicionar(cozinha);
		
		return cozinhaDTOAssembler.toModel(cozinha);
	}

	@ApiOperation("Atualiza uma cozinha por ID")
	@PutMapping("/{cozinhaId}")
//	@ResponseStatus(HttpStatus.Up)
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId,
			@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		
		Cozinha cozinhaAtual = cadastroCozinha.buscaOuFalha(cozinhaId);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);
		cozinhaAtual = cadastroCozinha.adicionar(cozinhaAtual);
		
		return cozinhaDTOAssembler.toModel(cozinhaAtual);
	}
	
	@ApiOperation("Exclui uma cozinha por ID")
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){
		cadastroCozinha.excluir(cozinhaId);
	}

}
