package com.wantfood.aplication.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wantfood.aplication.domain.model.Produto;
import com.wantfood.aplication.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
    @Query("from Produto where restaurante.id = :restaurante and id = :produto")
    Optional<Produto> findById(@Param("restaurante") Long restauranteId, 
            @Param("produto") Long produtoId);
    
    //Buscando todos os restaurantes
    List<Produto> findByRestaurante(Restaurante restaurante);
    
    //Buscando todos os restaurante que tem a propriedade ativo como true
    @Query("from Produto p where p.ativo = true and restaurante = :restaurante")
    List<Produto> findAtivoByRestaurante(Restaurante restaurante);
}
