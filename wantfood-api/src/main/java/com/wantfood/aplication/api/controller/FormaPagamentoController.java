package com.wantfood.aplication.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping //Mapeando o metodo listar para quando fizerem uma requisição get
	public List<FormaPagamentoDTO> listar(){
		List<FormaPagamento> todasFormaPagamento = formaPagamentoRepository.findAll();
		
		return formaPagamentoDTOAssembler.toCollectionModel(todasFormaPagamento);
	}
	
	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO buscar(@PathVariable Long formaPagamentoId){
		
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscaOuFalha(formaPagamentoId);
		
		return formaPagamentoDTOAssembler.toModel(formaPagamento);
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
