package com.wantfood.aplication.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wantfood.aplication.api.assembler.FormaPagamentoDTOAssembler;
import com.wantfood.aplication.api.assembler.FormaPagamentoInputDisassembler;
import com.wantfood.aplication.api.model.FormaPagamentoDTO;
import com.wantfood.aplication.api.model.input.FormaPagamentoInputDTO;
import com.wantfood.aplication.domain.model.FormaPagamento;
import com.wantfood.aplication.domain.repository.FormaPagamentoRepository;
import com.wantfood.aplication.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	
	
	/*
	 * Mapeando o metodo listar para quando fizerem uma requisição get
	 * Retornando um ResponseEntity de uma lista de forma de pagamento para poder alterar
	 * o cabeçalho da resposta
	 * 
	 * Colocando um cache no cabeçalho para deixar em cache por 10 s, para não precisar fazer novos 
	 * selects durante esse tempo
	 * 
	 * ------------------------- No return do método -------------------------
	 * 
	 * .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate()), deixa o cache 
	 * restrito apenas para amarzenar no cahce local
	 *
	 * .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachPublic()), deixa o cache publico
	 * para armazenar no cache compartilhado e local
	 * 
	 * .cacheControl(CacheControl.noCache()), valida toda a vez que é feito a requisição,
	 * por exemplo, a validação sem onoCahce ocorre quando o estado do cache se torna stale,
	 * porém com o noCache, a validação ocorro toda vez que é feita uma requisição.
	 * 
	 * .cacheControl(CacheControl.noStore()), não permite que nenhuma resposta seja salva
	 * em nenhum cache
	 * */
	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> listar(){
		List<FormaPagamento> todasFormaPagamento = formaPagamentoRepository.findAll();
		
		List<FormaPagamentoDTO> formasPagamentoModel = formaPagamentoDTOAssembler
				.toCollectionModel(todasFormaPagamento);

		return ResponseEntity.ok()
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
//				.cacheControl(CacheControl.noCache())
//				.cacheControl(CacheControl.noStore())
				.body(formasPagamentoModel);
	}
	
	/*
	 * Adicionando um cabeçalho de resposta, colocando os dados em uma cache durante 10 segundos
	 * testando pelo Talend API Tester extensão do chrome
	 * */
	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId){
		
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscaOuFalha(formaPagamentoId);
		
		FormaPagamentoDTO formaPagamentoModel = formaPagamentoDTOAssembler.toModel(formaPagamento);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formaPagamentoModel);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler
				.toDomainObject(formaPagamentoInputDTO);
		
		formaPagamento = cadastroFormaPagamentoService.adicionar(formaPagamento);
		
		return formaPagamentoDTOAssembler.toModel(formaPagamento);
	}
	
    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
            @RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
        FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscaOuFalha(formaPagamentoId);
        
        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInputDTO, formaPagamentoAtual);
        
        formaPagamentoAtual = cadastroFormaPagamentoService.adicionar(formaPagamentoAtual);
        
        return formaPagamentoDTOAssembler.toModel(formaPagamentoAtual);
    }
    
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamentoService.excluir(formaPagamentoId);	
    }
}
