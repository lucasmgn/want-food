package com.wantfood.aplication.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wantfood.aplication.domain.model.FotoProduto;
import com.wantfood.aplication.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProtudoService {
	
	//Utilizando o repositorio de produto pq FotoProduto está dentro do agregado de Produto
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto) {
		/*
		 * Caso tente salvar novamente a foto,
		 * a antiga será e a nova será adicionada, excluir se existir
		 * */
		Long restauaranteId = foto.getProduto().getRestaurante().getId();
		Long produtoId = foto.getProduto().getId();
		
		Optional<FotoProduto> fotoExistente = produtoRepository
				.findFotoProdutoById(restauaranteId,produtoId);
		
		if(fotoExistente.isPresent()) {
			//ecluir foto
			produtoRepository.delete(fotoExistente.get());
		}
				
		return produtoRepository.save(foto);
	}
}
