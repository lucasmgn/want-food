package com.wantfood.aplication.domain.repository;

import com.wantfood.aplication.domain.model.PhotoProduct;

//Interface criada para agregar photoProduct, será herdada na interface de repositorio de product
public interface ProductRepositoryQueries {

	PhotoProduct save(PhotoProduct photoProduct);
	void delete(PhotoProduct photo);

}