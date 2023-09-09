package com.wantfood.aplication.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wantfood.aplication.domain.model.PhotoProduct;
import com.wantfood.aplication.domain.repository.ProductRepositoryQueries;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public PhotoProduct save(PhotoProduct photoProduct) {
		return entityManager.merge(photoProduct);
	}

	@Transactional
	@Override
	public void delete(PhotoProduct photo) {
		entityManager.remove(photo);
	}
}
