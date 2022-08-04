package com.wantfood.aplication.domain.repository;

import com.wantfood.aplication.domain.model.FotoProduto;

//Interface criada para agregar fotoProduto, será herdada na interface de repositorio de produto
public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto fotoProduto);
	void delete(FotoProduto foto);

}