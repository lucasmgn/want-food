package com.wantfood.aplication.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.wantfood.aplication.domain.exception.EntidadeEmUsoException;
import com.wantfood.aplication.domain.exception.EntidadeNaoEncontradaException;
import com.wantfood.aplication.domain.model.Cozinha;
import com.wantfood.aplication.domain.repository.CozinhaRepository;
import com.wantfood.aplication.domain.service.CadastroCozinhaService;

//Possui o @ResponsyBody e o @Controller
@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	// Mapeando o metodo listar para quando fizerem uma requisição get
	// Colocando os valores em json
	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.todas();
	}

	@GetMapping(value = "/{cozinhaId}")
	public ResponseEntity<Cozinha> busca(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaRepository.porId(cozinhaId);
		if (cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}

		// return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.notFound().build();

		/*
		 * public ResponseEntity<Cozinha> busca (@PathVariable Long cozinhaId) { Cozinha
		 * cozinha = cozinhaRepository.porId(cozinhaId); return
		 * ResponseEntity.status(HttpStatus.OK).body(cozinha); //responde o status ok e
		 * o body de cozinha return ResponseEntity.ok(cozinha);
		 */
	}

	/*
	 * Colocando o status como create (status 201), e irá retornar a cozinha criada
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinha.adicionar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
//	@ResponseStatus(HttpStatus.Up)
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cozinhaRepository.porId(cozinhaId);

		if (cozinhaAtual != null) {
			// Copiando os valores da cozinha para cozinha atual
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

			cadastroCozinha.adicionar(cozinhaAtual);
			return ResponseEntity.ok(cozinhaAtual);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId){
		try{
			cadastroCozinha.excluir(cozinhaId);	
			return ResponseEntity.noContent().build();
			
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}
