package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.FormPaymentDTOAssembler;
import com.wantfood.aplication.api.assembler.FormPaymentInputDisassembler;
import com.wantfood.aplication.api.model.FormPaymentDTO;
import com.wantfood.aplication.api.model.input.FormPaymentInputDTO;
import com.wantfood.aplication.domain.repository.FormPaymentRepository;
import com.wantfood.aplication.domain.service.RegistrationFormPaymentService;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
@RequiredArgsConstructor
public class FormPaymentController {
	
	private final FormPaymentRepository formPaymentRepository;
	
	private final RegistrationFormPaymentService registrationFormPaymentService;
	
	private final FormPaymentDTOAssembler formPaymentDTOAssembler;
	
	private final FormPaymentInputDisassembler formPaymentInputDisassembler;
	
	
	/*
	 * Mapeando o metodo list para quando fizerem uma requisição get
	 * Retornando um ResponseEntity de uma lista de forma de pagamento para poder alterar
	 * o cabeçalho da resposta
	 * 
	 * Colocando um cache no cabeçalho para deixar em cache por 10 s, para não precisar fazer news 
	 * selects durante esse tempo
	 * 
	 * ------------------------- No return do método -------------------------
	 * 
	 * .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate()), deixa o cache 
	 * restrito apenas para amarzenar no cahce local
	 *
	 * .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachPublic()), deixa o cache publico
	 * para store no cache compartilhado e local
	 * 
	 * .cacheControl(CacheControl.noCache()), valida toda a vez que é feito a requisição,
	 * por exemplo, a validação sem onoCahce ocorre quando o state do cache se torna stale,
	 * porém com o noCache, a validação ocorro toda vez que é feita uma requisição.
	 * 
	 * .cacheControl(CacheControl.noStore()), não permite que nenhuma resposta seja salva
	 * em nenhum cache
	 * 
	 * if(request.checkNotModified(eTag)) pegará do cabeçalho da requisição e fará a comparação do
	 * if-none-macth com esse eTag. Se retornar null, não irá precisar fazer o processo, já que o
	 * eTag(ataAtualizacao) é igual a data que está do BD
	 * 
	 * Enquanto não mudar os dados de formPayment ele irá somente validar, não refazendo
	 * todo o select da tabela
	 * */
	
	@GetMapping
	public ResponseEntity<List<FormPaymentDTO>> list(ServletWebRequest request){
		//Desabilitando o Shallow Etag para poder utilizar o Deep ETags
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		//Se retornar nulo, a eTag será igual a 0
		var eTag = "0";
			
		var dataUltimaAtualizacao = formPaymentRepository.getDataUltimaAtualizacao();
		
		if(dataUltimaAtualizacao != null) {
			//Retorna o number de segundos desde 1970, transformando em String
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		var todasformPayment = formPaymentRepository.findAll();
		
		var paymentMethodsModel = formPaymentDTOAssembler
				.toCollectionModel(todasformPayment);

		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(paymentMethodsModel);
	}
	
	/*
	 * Adicionando um cabeçalho de resposta, colocando os dados em uma cache durante 10 segundos
	 * testando pelo Talend API Tester extensão do chrome
	 * */
	@GetMapping("/{formPaymentId}")
	public ResponseEntity<FormPaymentDTO> find(@PathVariable Long formPaymentId,
												 ServletWebRequest request){
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		var eTag = "0";
		
		var dateUpdate = formPaymentRepository
	            .getDataUltimaAtualizacaoById(formPaymentId);
		
		if(dateUpdate != null) {
			eTag = String.valueOf(dateUpdate.toEpochSecond()); 
		}
		if(request.checkNotModified(eTag)){
			return null;
		}
		
		var formPayment = registrationFormPaymentService.fetchOrFail(formPaymentId);
		
		var formPaymentModel = formPaymentDTOAssembler.toModel(formPayment);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formPaymentModel);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormPaymentDTO add(@RequestBody @Valid FormPaymentInputDTO formPaymentInputDTO) {
		var formPayment = formPaymentInputDisassembler
				.toDomainObject(formPaymentInputDTO);
		
		formPayment = registrationFormPaymentService.add(formPayment);
		
		return formPaymentDTOAssembler.toModel(formPayment);
	}
	
    @PutMapping("/{formPaymentId}")
    public FormPaymentDTO atualizar(@PathVariable Long formPaymentId,
									@RequestBody @Valid FormPaymentInputDTO formPaymentInputDTO) {
        var formPaymentAtual = registrationFormPaymentService.fetchOrFail(formPaymentId);
        
        formPaymentInputDisassembler.copyToDomainObject(formPaymentInputDTO, formPaymentAtual);
        
        formPaymentAtual = registrationFormPaymentService.add(formPaymentAtual);
        
        return formPaymentDTOAssembler.toModel(formPaymentAtual);
    }
    
    @DeleteMapping("/{formPaymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long formPaymentId) {
        registrationFormPaymentService.delete(formPaymentId);
    }
}
