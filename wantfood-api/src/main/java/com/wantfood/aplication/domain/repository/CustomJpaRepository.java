package com.wantfood.aplication.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID>{
	
	//Metodo implementado no customJpaRepositoryImpl
	Optional<T> findPrimeiro();
	
	//criando método para evitar bugs de gerenciamento, metodo implementado no customJpaRepositoryImpl
	void detach(T entity);
}
