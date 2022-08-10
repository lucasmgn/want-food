package com.wantfood.aplication.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wantfood.aplication.domain.exception.FotoProdutoNaoEncontradaException;
import com.wantfood.aplication.domain.model.FotoProduto;
import com.wantfood.aplication.domain.repository.ProdutoRepository;
import com.wantfood.aplication.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProtudoService {
	
	//Utilizando o repositorio de produto pq FotoProduto está dentro do agregado de Produto
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService storageService;

	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoProdutoById(restauranteId, produtoId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
		
	}
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream inputStream) {
		/*
		 * Caso tente salvar novamente a foto,
		 * a antiga será e a nova será adicionada, excluir se existir
		 * */
		Long restauaranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String nomeNovoArquivo = storageService.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		Optional<FotoProduto> fotoExistente = produtoRepository
				.findFotoProdutoById(restauaranteId,produtoId);
		
		if(fotoExistente.isPresent()) {
			//excluir foto do bd e da maquina local
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}
		
		//Salvando a foto no bd antes de armazenar, para que se der algum problema
		//ele venha antes do armazenamento, fazendo um flush 
		foto.setNomeArquivo(nomeNovoArquivo);
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.contentType(foto.getContentType())
				.inputStream(inputStream)
				.build();
		
		storageService.substituir(nomeArquivoExistente ,novaFoto);
				
		return foto;
	}
	
	@Transactional
	public void remover(Long restauranteId, Long produtoId) {
		
			FotoProduto fotoRemover = buscarOuFalhar(restauranteId, produtoId);
			
			produtoRepository.delete(fotoRemover);
			produtoRepository.flush();
			
			storageService.remover(fotoRemover.getNomeArquivo());
	}
}
